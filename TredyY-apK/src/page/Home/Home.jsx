import { Button } from "@/components/ui/button";
import React, { useEffect, useState } from "react";
import AssetTable from "./AssetTable";
import Stockchart from "./Stockchart";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Cross1Icon, DotIcon } from "@radix-ui/react-icons";
import { MessageCircle } from "lucide-react";
import { Input } from "@/components/ui/input";
import { getBitcoinList, getTop50BitcoinList } from "@/state/Bitcoin/Action";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";

import { useDispatch, useSelector } from "react-redux";
import { store } from "@/state/Store";

const Home = () => {
  const [category, setCategory] = useState("all");
  const [chatbotVisible, setChatbotVisible] = useState(false); 
  const [inputValue, setInputValue] = useState(""); 
  const [messages, setMessages] = useState([
    {
      role: "bot",
      content: "Hi smkcoolllll_, you can ask me anything about crypto.",
    },
  ]); 
  const { bitCoin } = useSelector((store) => store);
  const dispatch = useDispatch();
  const handleCategory = (value) => {
    setCategory(value);
  };

  const toggleChatbot = () => {
    setChatbotVisible(!chatbotVisible);
  };

  const handleChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter" && inputValue.trim()) {
      const newMessage = { role: "user", content: inputValue };
      setMessages((prevMessages) => [...prevMessages, newMessage]); 

      const botResponse = {
        role: "bot",
        content: `You asked: ${inputValue}. This is a bot response!`,
      };
      setMessages((prevMessages) => [...prevMessages, newMessage, botResponse]);

      setInputValue("");
    }
  };

  useEffect(() => {
    dispatch(getTop50BitcoinList());
  }, [category]);

  useEffect(() => {
    dispatch(getBitcoinList(1));
  }, []);

  return (
    <div className="relative">
      <div className="lg:flex">
        <div className="lg:w-[50%] lg:border-r">
          <div className="p-3 flex items-center gap-4">
            <Button
              onClick={() => handleCategory("all")}
              variant={category === "all" ? "default" : "outline"}
              className="rounded-full"
            >
              All
            </Button>
            <Button
              onClick={() => handleCategory("top50")}
              variant={category === "top50" ? "default" : "outline"}
              className="rounded-full"
            >
              Top 50
            </Button>
            <Button
              onClick={() => handleCategory("topGainers")}
              variant={category === "topGainers" ? "default" : "outline"}
              className="rounded-full"
            >
              Top Gainers
            </Button>
            <Button
              onClick={() => handleCategory("topLosers")}
              variant={category === "topLosers" ? "default" : "outline"}
              className="rounded-full"
            >
              Top Losers
            </Button>
          </div>
          <AssetTable
            bitCoin={category === "all" ? bitCoin.coinList : bitCoin.top50}
            category={category}
          />
          <div>
            <Pagination>
              <PaginationContent>
                <PaginationItem>
                  <PaginationPrevious href="#" />
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#">1</PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationEllipsis />
                </PaginationItem>
                <PaginationItem>
                  <PaginationNext href="#" />
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          </div>
        </div>

        <div className="hidden lg:block lg:w-[50%] p-5">
          <Stockchart bitcoinId={"bitcoin"} />

          <div>
            <div>
              <Avatar>
                <AvatarImage
                  src={
                    "https://coin-images.coingecko.com/coins/images/325/large/Tether.png?1696501661"
                  }
                />
              </Avatar>
            </div>
            <div className="flex items-center gap-2">
              <p>USDT</p>
              <DotIcon className="text-grey-400" />
              <p className="text-grey-400">Tether</p>
            </div>
            <div className="flex items-end gap-2">
              <p className="text-xl font-bold">0.999162</p>
              <p className="text-red-600">
                <span>-89574149.1278076</span>
                <span>(-0.07583%)</span>
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Chatbot Section */}
      {chatbotVisible && (
        <div className="fixed bottom-16 right-5 z-40 rounded-md w-[20rem] md:w-[25rem] lg:w-[25rem] h-[70vh] bg-slate-900">
          <div className="flex justify-between items-center border-b px-6 h-[12%]">
            <p className="text-white">Chat Bot</p>
            <Button variant="ghost" size="icon" onClick={toggleChatbot}>
              <Cross1Icon className="text-white" />
            </Button>
          </div>
          <div className="h-[76%] flex flex-col overflow-y-auto gap-5 px-5 py-2 scroll-container">
            {messages.map((message, i) => (
              <div
                key={i}
                className={`${
                  message.role === "user" ? "self-end" : "self-start"
                } pb-5 w-auto`}
              >
                <div className="px-5 py-2 rounded-md bg-slate-800 w-auto">
                  <p>{message.content}</p>
                </div>
              </div>
            ))}
          </div>
          <div className="h-[12%] border-t flex items-center">
            <Input
              className="w-full h-full outline-none px-4 py-2"
              placeholder="Write a message..."
              value={inputValue}
              onChange={handleChange}
              onKeyPress={handleKeyPress}
            />
          </div>
        </div>
      )}

      {/* Chatbot Button */}
      <div className="fixed bottom-5 right-5 z-40">
        <Button
          className="w-[12rem] h-[3rem] flex items-center gap-2 justify-center rounded-full shadow-lg bg-blue-500 text-white hover:bg-blue-600 transition"
          onClick={toggleChatbot}
        >
          <MessageCircle size={24} className="fill-current" />
          <span className="text-lg">Chat Bot</span>
        </Button>
      </div>
    </div>
  );
};

export default Home;
