import { Route, Routes } from "react-router-dom";
import { useEffect } from "react";
import Home from "./page/Home/Home";
import Navbar from "./page/Navbar/Navbar";
import Portfolio from "./page/Portfolio/Portfolio";
import Activity from "./page/Activity/Activity";
import Wallet from "./page/Wallet/Wallet";
import Withdrawal from "./page/WithDrawal/Withdrawal";
import Stockdetails from "./page/StockDetails/Stockdetails";
import Watchlist from "./page/Watchlist/Watchlist";
import Profile from "./page/Profile/Profile";
import PaymentDetail from "./page/PaymentDetails/PaymentDetail";
import Auth from "./page/Auth/Auth";
import { useDispatch, useSelector } from "react-redux";
import { getUserProfile, loginSuccess } from "./state/Auth/Action"; 

function App() {
  const { auth } = useSelector((store) => store);
  const dispatch = useDispatch();

  useEffect(() => {
    const token = auth.jwt || localStorage.getItem("jwt");

    if (token) {
      dispatch(loginSuccess(token));  // Set jwt and update isAuthenticated
      dispatch(getUserProfile());     // Fetch user profile if token exists
    }
  }, [auth.jwt, dispatch]);

  return (
    <>
      {auth?.user ? (  // Render routes if auth.user exists
        <div>
          <Navbar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/portfolio" element={<Portfolio />} />
            <Route path="/activity" element={<Activity />} />
            <Route path="/wallet" element={<Wallet />} />
            <Route path="/withdrawal" element={<Withdrawal />} />
            <Route path="/payment_details" element={<PaymentDetail />} />
            <Route path="/market/:id" element={<Stockdetails />} />
            <Route path="/watchlist" element={<Watchlist />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/search" element={<Portfolio />} />
          </Routes>
        </div>
      ) : (
        <Auth />  // Render Auth component if auth.user does not exist
      )}
    </>
  );
}

export default App;
