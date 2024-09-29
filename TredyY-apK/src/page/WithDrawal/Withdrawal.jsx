import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { store } from "@/state/Store";
import { getWithdrawalHistory } from "@/state/Withdrawal/Action";
import { Avatar, AvatarImage } from "@radix-ui/react-avatar";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
const Withdrawal = () => {
  const dispatch = useDispatch();
  const { wallet, withdrawal } = useSelector((store) => store);

  useEffect(() => {
    dispatch(getWithdrawalHistory(localStorage.getItem("jwt")));
  }, []);
  return (
    <div className="p-5 lg:p-20">
      <h5 className="font-bold text-3xl pb-6">WithDrawal</h5>
      <Table className="border">
        <TableHeader>
          <TableRow>
            <TableHead className="py-5">Date </TableHead>
            <TableHead>Method</TableHead>
            <TableHead>Amount</TableHead>

            <TableHead className="text-right ">Status</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {withdrawal.history.map((item, index) => (
            <TableRow key={index}>
              <TableCell>
                <p>{item.date.toString()}</p>
              </TableCell>

              <TableCell className="">Bank</TableCell>
              <TableCell className="">${item.amount}</TableCell>
              <TableCell className="text-right">{item.withdrawalStatus}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default Withdrawal;
