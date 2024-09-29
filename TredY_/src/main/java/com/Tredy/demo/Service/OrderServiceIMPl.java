package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.OrderStatus;
import com.Tredy.demo.Domain.OrderType;
import com.Tredy.demo.Model.*;
import com.Tredy.demo.Repository.OrderItemRepo;
import com.Tredy.demo.Repository.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceIMPl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private  WalletService walletService;

    @Autowired
    private AssetService assetService;
    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price=orderItem.getBitcoin().getCurrentPrice()*orderItem.getQuantity();
        Order order=new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);

        return orderRepo.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepo.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepo.findByUserUserId(userId);
    }

    private OrderItem createOrderItem(Bitcoin bitcoin,double quantity,double buyPrice,double sellPrice){
        OrderItem orderItem=new OrderItem();
        orderItem.setBitcoin(bitcoin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepo.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Bitcoin bitcoin,double quantity,User user) throws  Exception{
        if(quantity<=0){
            throw  new Exception("Quantity should be greater than 0");
        }
        double buyPrice=bitcoin.getCurrentPrice();
        OrderItem orderItem=createOrderItem(bitcoin,quantity,buyPrice,0);
        Order order=createOrder(user,orderItem,OrderType.BUY);
        orderItem.setOrder(order);
        walletService.payOrderPayment(order,user);
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order saveOrder=orderRepo.save(order);

        Asset oldAsset=assetService.findAssetByUserIdAndBitcoinId(order.getId(),order.getOrderItem().getBitcoin().getId());
        if(oldAsset==null){
            assetService.createAsset(user,orderItem.getBitcoin(),orderItem.getQuantity());
        }else {
            assetService.updateAsset(oldAsset.getId(),quantity);
        }
        return saveOrder;
    }

    @Transactional
    public Order sellAsset(Bitcoin bitcoin,double quantity,User user) throws  Exception{
        if(quantity<=0){
            throw  new Exception("Quantity should be greater than 0");
        }
        double sellPrice=bitcoin.getCurrentPrice();
        Asset assetToSell=assetService.findAssetByUserIdAndBitcoinId(user.getUserId(),bitcoin.getId());
        double buyPrice=assetToSell.getBuyPrice();

        if(assetToSell!=null) {


            OrderItem orderItem = createOrderItem(bitcoin, quantity, buyPrice, sellPrice);
            Order order = createOrder(user, orderItem, OrderType.SELL);
            orderItem.setOrder(order);
            if (assetToSell.getQuantity() >= quantity) {
                order.setOrderStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Order saveOrder = orderRepo.save(order);
                walletService.payOrderPayment(order, user);
                Asset updateAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
                if (updateAsset.getQuantity() * bitcoin.getCurrentPrice() <= 1) {
                    assetService.deleteAsset(updateAsset.getId());
                }
                return saveOrder;
            }

            throw new Exception("Insufficient quantity to sell");
        }
        throw  new Exception("Asset not found");
    }

    @Override
    @Transactional
    public Order processOrder(Bitcoin bitcoin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType.equals(OrderType.BUY)){
            return buyAsset(bitcoin, quantity, user);
        } else if (orderType.equals(OrderType.SELL)) {
            return sellAsset(bitcoin,quantity,user);
        }
        throw  new Exception("Invalid order Type");
    }
}
