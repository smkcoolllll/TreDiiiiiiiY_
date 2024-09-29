import React from "react";
import Sidebar from "./Sidebar";
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import {
  DragHandleHorizontalIcon,
  MagnifyingGlassIcon,
} from "@radix-ui/react-icons";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarImage } from "@radix-ui/react-avatar";
import { AvatarFallback } from "@/components/ui/avatar";
import { useSelector } from "react-redux"; // Import to access user from redux
import { store } from "@/state/Store";
import Auth from "../Auth/Auth";

const Navbar = () => {
  const { auth } = useSelector((store) => store);

  return (
    <div className="px-2 py-3 border-b z-50 bg-background bg-opacity-0 sticky top-0 left-0 right-0 flex justify-between items-center">
      <div className="flex items-center gap-3">
        <Sheet>
          <SheetTrigger>
            <Button
              variant="ghost"
              size="icon"
              className="p-0 bg-transparent hover:bg-transparent"
            >
              <DragHandleHorizontalIcon className="h-7 w-7 text-current" />
            </Button>
          </SheetTrigger>
          <SheetContent
            className="w-72 border-r-0 flex flex-col justify-center"
            side="left"
          >
            <SheetHeader>
              <SheetTitle>
                <div className="text-1xl flex justify-center items-center gap-0">
                  <Avatar>
                    <AvatarImage src="https://cdn.pixabay.com/photo/2018/03/15/11/29/bitcoin-3227945_1280.png" />
                  </Avatar>
                  <div>
                    <span className="font-bold text-red-700">smkcoolllll_</span>
                    <span>TreDyY</span>
                  </div>
                </div>
              </SheetTitle>
            </SheetHeader>
            <Sidebar />
          </SheetContent>
        </Sheet>
        <p className="text-sm lg:text-base cursor-pointer">TreDyY</p>
        <div className="p-0 ml-9">
          <Button
            variant="outline"
            className="flex items-center gap-3 rounded-full p-2"
          >
            <MagnifyingGlassIcon />
            <span>Search</span>
          </Button>
        </div>
      </div>
      <div>
        <Avatar>
          <AvatarFallback>
            {auth.user?.fullName[0].toUpperCase()}
          </AvatarFallback>
        </Avatar>
      </div>
    </div>
  );
};

export default Navbar;
