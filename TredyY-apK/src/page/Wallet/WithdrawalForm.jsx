import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { withdrawalRequest } from "@/state/Withdrawal/Action";
import { DialogClose } from "@radix-ui/react-dialog";
import React from "react";
import { useDispatch, useSelector } from "react-redux";

const WithdrawalForm = () => {
  const [amount, setAmount] = React.useState("");
  const dispatch = useDispatch();
  const { wallet,withdrawal } = useSelector((store) => store);
  const [paymentMethod, setPaymentmethod] = React.useState("RAZORPAY");

  const handlePaymentMethodChange = (value) => {
    setPaymentmethod(value);
  };

  const handleChange = (e) => {
    setAmount(e.target.value);
  };

  const handlesubmit = () => {
    dispatch(withdrawalRequest({amount,jwt:localStorage.getItem("jwt")}))
    console.log(amount, paymentMethod);
  };
  return (
    <div className="pt-10 space-y-5">
      <div className="flex justify-between items-center rounded-md bg-slate-900 text-xl font-bold px-5 py-4">
        <p>Available balance</p>
        <p>$9000</p>
      </div>
      <div className="flex flex-col items-center">
        <h1>Enter withdrawal amount</h1>
        <div className="flex flex-col items-center justify-center">
          <Input
            onChange={handleChange}
            value={amount}
            className="withdrawalInput py-7 border-none outline-none focus:outline-none px-0 text-2xl text-center"
            placeholder="$8989"
            type="number"
          />
        </div>
      </div>
      <div>
        <p className="pb-2">Transfer To</p>
        <div className="flex items-center gap-5 border px-5 py-2 rounded-md">
          <img
            className="h-9 w-9"
            src="https://cdn-icons-png.flaticon.com/128/1138/1138038.png"
            alt=""
          />
          <div>
            <p className="text-xl font-bold">{withdrawal.paymentDetails?.bankName}</p>
            <p className="text-xs">{withdrawal.paymentDetails?.accountNumber}</p>
          </div>
        </div>
      </div>
      <DialogClose className="w-full">
        <Button onClick={handlesubmit} className="w-full py-7 text-xl">
          withdraw
        </Button>
      </DialogClose>
    </div>
  );
};

export default WithdrawalForm;
