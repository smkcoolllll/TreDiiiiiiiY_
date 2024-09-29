import { Button } from "@/components/ui/button";
import { ActivityLogIcon, DashboardIcon, ExitIcon, HomeIcon, PersonIcon } from "@radix-ui/react-icons";
import { SheetClose } from "@/components/ui/sheet";
import { BookmarkIcon, CreditCardIcon, PiggyBankIcon, WalletIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux"; // Import useDispatch
import { logout } from "@/state/Auth/Action"; // Import the logout action

const menu = [
  { name: "Home", path: "/", icon: <HomeIcon className="h-6 w-6" /> },
  { name: "Portfolio", path: "/portfolio", icon: <DashboardIcon className="h-6 w-6" /> },
  { name: "Watchlist", path: "/watchlist", icon: <BookmarkIcon className="h-6 w-6" /> },
  { name: "Activity", path: "/activity", icon: <ActivityLogIcon className="h-6 w-6" /> },
  { name: "Wallet", path: "/wallet", icon: <WalletIcon className="h-6 w-6" /> },
  { name: "Payment Details", path: "/payment_details", icon: <PiggyBankIcon className="h-6 w-6" /> },
  { name: "Withdraw", path: "/withdrawal", icon: <CreditCardIcon className="h-6 w-6" /> },
  { name: "Dashboard", path: "/profile", icon: <PersonIcon className="h-6 w-6" /> },
  { name: "Logout", path: null, icon: <ExitIcon className="h-6 w-6" /> }, // No path, handled separately
];

const Sidebar = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch(); // Initialize dispatch

  const handleMenuClick = (item) => {
    if (item.name === "Logout") {
      dispatch(logout()); 
      navigate("/auth");
      console.log("logged out successfulyy");
    } else {
      navigate(item.path);
    }
  };

  return (
    <div className="mt-10 space-y-5">
      {menu.map((item, index) => (
        <div key={index}>
          <SheetClose className="w-full">
            <Button
              variant="outline"
              className="flex items-center gap-5 py-6 w-full"
              onClick={() => handleMenuClick(item)} // Handle click with the new function
            >
              <span className="w-8">{item.icon}</span>
              <p>{item.name}</p>
            </Button>
          </SheetClose>
        </div>
      ))}
    </div>
  );
};

export default Sidebar;
