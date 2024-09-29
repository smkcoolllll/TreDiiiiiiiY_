import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { getUserAssets } from "@/state/Asset/Action";
import { store } from "@/state/Store";
import { Avatar, AvatarImage } from "@radix-ui/react-avatar";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
const Portfolio = () => {
  const dispatch = useDispatch();
  const { asset } = useSelector(store=> store)
  useEffect(() => {
    dispatch(getUserAssets(localStorage.getItem("jwt")));
  }, []);
  return (
    <div className="p-5 lg:p-20">
      <h5 className="font-bold text-3xl pb-6">PORTFOLIO</h5>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="">Asset</TableHead>
            <TableHead>Price</TableHead>
            <TableHead>Unit</TableHead>
            <TableHead>Change</TableHead>
            <TableHead>Change%</TableHead>

            <TableHead className="text-right">Volume</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {asset.userAssets.map((item, index) => (
            <TableRow key={index}>
              <TableCell className="font-medium flex items-center gap-2">
                <Avatar className="w-9 h-8">
                  <AvatarImage src={item.bitCoin.image} />
                </Avatar>
                <span>{item.bitCoin.name}</span>
              </TableCell>
              <TableCell>{item.bitCoin.symbol.toUppercase()}</TableCell>
              <TableCell>{item.quantity}</TableCell>
              <TableCell>{item.bitCoin.price_change_percentage_24h}</TableCell>
              <TableCell>{item.bitCoin.price_change_percentage_24h}</TableCell>
              <TableCell className="text-right">
                {item.bitCoin.total_volume}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default Portfolio;
