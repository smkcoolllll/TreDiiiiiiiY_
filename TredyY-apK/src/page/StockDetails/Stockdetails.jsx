import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { BookmarkFilledIcon, BookmarkIcon, DotIcon } from "@radix-ui/react-icons";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import TreadingForm from "./TreadingForm";
import Stockchart from "../Home/Stockchart";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import { fetchBitcoinDetails } from "@/state/Bitcoin/Action";

const Stockdetails = () => {
  const bitcoinDetails = useSelector((state) => state.bitCoin.bitcoinDetails); // Access only the bitcoin details
  const dispatch = useDispatch();
  const { bitcoinId } = useParams();

  useEffect(() => {
    const jwt = localStorage.getItem('jwt'); // Get JWT token from localStorage
    if (bitcoinId && jwt) {
      dispatch(fetchBitcoinDetails({ bitcoinId, jwt })); // Dispatch action with bitcoinId and jwt
    }
  }, [bitcoinId, dispatch]);

  return (
    <div className="space-y-10 mt-6">
      {/* Flex container for Avatar, Stock Details, Bookmark, and Dialog */}
      <div className="flex items-center justify-between w-full gap-5">
        {/* First div for Avatar and Stock Details */}
        <div className="flex gap-5 items-center">
          <Avatar>
            <AvatarImage src={bitcoinDetails?.image.large} alt="Bitcoin Avatar" />
          </Avatar>
          <div className="flex flex-col">
            <div className="flex items-center gap-2">
              <p className="text-lg font-medium">{bitcoinDetails?.symbol.toUpperCase()}</p>
              <DotIcon className="text-gray-400" />
              <p className="text-gray-600">{bitcoinDetails?.name}</p>
            </div>
            <div className="flex items-end gap-2">
              <p className="text-xl font-bold">
                ${bitcoinDetails?.market_data?.current_price?.usd}
              </p>
              <p className="text-red-600">
                <span>-{bitcoinDetails?.market_data?.market_cap_change_24h}</span>
                <span className="ml-1">(-{bitcoinDetails?.market_data?.market_cap_change_percentage_24h}%)</span>
              </p>
            </div>
          </div>
        </div>

        {/* Second div for Bookmark button and Dialog */}
        <div className="ml-auto flex gap-5 items-center">
          <Button>
            {true ? (
              <BookmarkFilledIcon className="h-6 w-6" />
            ) : (
              <BookmarkIcon className="h-6 w-6" />
            )}
          </Button>

          <Dialog>
            <DialogTrigger>
              <Button size="lg">Trade</Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>How much do you want to spend?</DialogTitle>
              </DialogHeader>
              <TreadingForm />
            </DialogContent>
          </Dialog>
        </div>
      </div>

      {/* Stock chart placed below the flex container */}
      <div className="mt-20 w-full">
        <Stockchart bitcoinId={bitcoinId} />
      </div>
    </div>
  );
};

export default Stockdetails;
