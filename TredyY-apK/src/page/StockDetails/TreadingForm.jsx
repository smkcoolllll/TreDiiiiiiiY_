import React, { useEffect, useState } from "react"; // Ensure React is imported
import { Input } from "@/components/ui/input"; // Ensure the path is correct
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { DotIcon } from "@radix-ui/react-icons";
import { Button } from "@/components/ui/button";
import { useDispatch, useSelector } from "react-redux";
import { store } from "@/state/Store";
import { getUserWallet } from "@/state/Wallet/Action";
import { getAssetDetails } from "@/state/Asset/Action";
import { payOrder } from "@/state/Order/Action";
import { data } from "autoprefixer";

const TreadingForm = () => {
  const [orderType, setOrderType] = useState("BUY");
  const dispatch = useDispatch();
  const [amount, setAmount] = useState(0);
  const [quantity, setQuantity] = useState(0);
  const { bitCoin, wallet, asset } = useSelector((store) => store);
  const handleChange = (e) => {
    const amount = e.target.value;
    setAmount(amount);
    if(bitCoin?.bitcoinDetails?.market_data?.current_price?.usd){
    const volume = calculateBuyCost(
      amount,
      bitCoin.bitcoinDetails.market_data.current_price.usd
    );
    console.log("Volume :", volume);

    setQuantity(volume);
  }
  };

  const calculateBuyCost = (amount, price) => {
    let volume = amount / price;
    let decimalPlaces = Math.max(2, price.toString().split(".")[0].length);
    return volume.toFixed(decimalPlaces);
  };
  useEffect(() => {
    dispatch(getUserWallet(localStorage.getItem("jwt")));
    if (bitCoin?.bitcoinDetails?.id) {
      dispatch(
        getAssetDetails({
          bitcoinId: bitCoin.bitcoinDetails.id,
          jwt: localStorage.getItem("jwt"),
        })
      );
    }
  }, [dispatch,bitCoin.bitcoinDetails]);

  const handleBuyCrypto=() => {
    dispatch(
      payOrder({
        jwt:localStorage.getItem('jwt'),
        amount,
        orderData:{
          bitcoinId:bitCoin?.bitcoinDetails?.id,
          quantity,orderType
        }
      })
    )
  }
  return (
    <div className="space-y-10 p-5">
      {/* Input and amount display */}
      <div>
        <div className="flex gap-4 items-center justify-between">
          <Input
            className="py-7 focus:outline-none"
            placeholder="Enter Amount..."
            onChange={handleChange}
            type="number"
            name="amount"
          />
          <div>
            <p className="border text-2xl flex justify-center items-center w-36 h-14 rounded-md">
              {quantity}
            </p>
          </div>
        </div>
        {false && (
          <h1 className="text-red-500 text-center pt-4">
            Insufficient wallet balance to buy
          </h1>
        )}
      </div>

      {/* Avatar and stock details */}
      <div className="flex gap-5 items-center">
        <Avatar>
          <AvatarImage
            src="https://coin-images.coingecko.com/coins/images/4128/large/solana.png?1718769756"
            alt="Solana"
          />
        </Avatar>
        <div className="flex flex-col">
          <div className="flex items-center gap-2">
            <p className="text-lg font-medium">SOL</p>
            <DotIcon className="text-gray-400" />
            <p className="text-gray-600">Solana</p>
          </div>
          <div className="flex items-end gap-2">
            <p className="text-xl font-bold">$132.71</p>
            <p className="text-red-600">
              <span>-61,943,331,081</span>
              <span className="ml-1">(-0.1367%)</span>
            </p>
          </div>
        </div>
      </div>

      {/* Order type and available info */}
      <div className="flex items-center justify-between">
        <p>Order Type</p>
        <p>Market Order</p>
      </div>
      <div className="flex items-center justify-between">
        <p>{orderType == "BUY" ? "Available Cash" : "Available Quantity"}</p>
        <p>
          {orderType == "BUY"
            ? wallet.userWallet?.balance
            : asset.assetDetails.quantity || 0}
        </p>
      </div>

      {/* Buttons for order actions */}
      <div>
        <Button
        onClick={handleBuyCrypto}
          className={`w-full py-6 ${
            orderType === "SELL" ? "bg-red-600 text-white" : ""
          }`}
        >
          {orderType}
        </Button>
        <Button
          variant="link"
          className="w-full mt-5 text-xl"
          onClick={() => setOrderType(orderType === "BUY" ? "SELL" : "BUY")}
        >
          {orderType === "BUY" ? "or SELL" : "or BUY"}
        </Button>
      </div>
    </div>
  );
};

export default TreadingForm;
