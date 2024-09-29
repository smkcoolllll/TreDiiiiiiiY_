import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import PaymentDetailForm from "./PaymentDetailForm";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getPaymentDetails } from "@/state/Withdrawal/Action";

const PaymentDetail = () => {
  const { withdrawal } = useSelector((store) => store);
  const dispatch = useDispatch();

  useEffect(() => {
    const jwt = localStorage.getItem("jwt");
    if (jwt) {
      dispatch(getPaymentDetails(jwt)); // Fetch payment details
    }
  }, [dispatch]);

  return (
    <div className="px-20">
      <h1 className="text-3xl font-bold py-10">Payment Details</h1>
      {withdrawal.paymentDetails ? (
        <Card>
          <CardHeader>
            <CardTitle>{withdrawal.paymentDetails.bankName || "Bank Name"}</CardTitle>
            <CardDescription>A/C NO: {withdrawal.paymentDetails.accountNumber}</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="flex items-center">
              <p className="w-32">A/C Holder</p>
              <h6>: {withdrawal.paymentDetails.accountHolderName}</h6>
            </div>
            <div className="flex items-center">
              <p className="w-32">IFSC</p>
              <p>: {withdrawal.paymentDetails.IFSC}</p>
            </div>
          </CardContent>
        </Card>
      ) : (
        <Dialog>
          <DialogTrigger className="py-3 px-6 bg-white border border-gray-300 rounded-lg shadow hover:bg-gray-100 focus:outline-none text-black">
            Add Payment Details
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Payment Details</DialogTitle>
            </DialogHeader>
            <PaymentDetailForm />
          </DialogContent>
        </Dialog>
      )}
    </div>
  );
};

export default PaymentDetail;
