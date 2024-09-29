import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { DotFilledIcon } from "@radix-ui/react-icons";
import React from "react";
import { useDispatch } from "react-redux";

const TopupForm = () => {
  const [amount, setAmount] = React.useState("");
  const [paymentMethod, setPaymentmethod] = React.useState("RAZORPAY");
  const dispatch = useDispatch();

  const handlePaymentMethodChange = (value) => {
    setPaymentmethod(value);
  };

  const handleChange = (e) => {
    setAmount(e.target.value);
  };

  const handlesubmit = () => {
    console.log(amount, paymentMethod);
    dispatch(
      paymentHandler({
        jwt: localStorage.getItem("jwt"),
        paymentMethod,
        amount,
      })
    );
  };
  return (
    <div className="pt-10 space-y-5">
      <div>
        <h1 className="pb-1">Enter Amount</h1>
        <Input
          onChange={handleChange}
          value={amount}
          className="py-7 text-lg"
          placeholder="$9999"
        />
      </div>
      <div>
        <h1 className="pb-1">select payment method</h1>
        <RadioGroup
          onValueChange={(value) => handlePaymentMethodChange(value)}
          className="flex"
          defaultValue="RAZORPAY"
        >
          <div className="flex items-center space-x-2 border p-3 px-5 rounded-md">
            <RadioGroupItem
              icon={DotFilledIcon}
              className="h-9 w-9"
              value="RAZORPAY"
              id="r1"
            />
            <Label htmlFor="r1">
              <div className="bg-white rounded-md px-5 py-2 w-32">
                <img
                  src="https://w7.pngwing.com/pngs/586/552/png-transparent-razorpay-hd-logo-thumbnail.png"
                  alt=""
                />
              </div>
            </Label>
          </div>

          <div className="flex items-center space-x-2 border p-3 px-5 rounded-md">
            <RadioGroupItem
              icon={DotFilledIcon}
              className="h-9 w-9"
              value="STRIPE"
              id="r2"
            />
            <Label htmlFor="r2">
              <div className="bg-white rounded-md px-5  w-32">
                <img
                  className="h-8"
                  src="data:image/png;base64,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"
                  alt=""
                />
              </div>
            </Label>
          </div>
        </RadioGroup>
      </div>
      <Button onClick={handlesubmit} className="w-full py-7">
        submit
      </Button>
    </div>
  );
};

export default TopupForm;
