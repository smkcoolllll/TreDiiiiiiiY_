import { Button } from "@/components/ui/button";
import "./Auth.css";
import SignUpForm from "./SignUpForm";
import { useLocation, useNavigate } from "react-router-dom";
import ForgotpassForm from "./ForgotpassForm";
import SignInForm from "./SignInForm";

const Auth = () => {
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <div className="h-screen relative authContainer">
      <div className="absolute top-0 right-0 left-0 bottom-0 bg-[#030712] bg-opacity-50">
        <div className="bgBlur absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 flex flex-col justify-center items-center h-[35rem] w-[30rem] rounded-md z-50 bg-black bg-opacity-50 shadow-2xl shadow-white px-10">
          <h1 className="text-4xl font-bold pb-9">smkcoolllll_TreDyY</h1>
          {location.pathname === "/signUp" ? (
            <section>
              <SignUpForm />
              <div className="flex items-center justify-center mt-2 space-x-2">
                <span>Have an account?</span>
                <Button onClick={() => navigate("/signIn")} variant="ghost">
                  Sign In
                </Button>
              </div>
            </section>
          ) : location.pathname === "/forgotPass" ? (
            <section>
              <ForgotpassForm />
              <div className="flex items-center justify-center mt-2 space-x-2">
                <span>Back to login?</span>
                <Button onClick={() => navigate("/signIn")} variant="ghost">
                  Sign In
                </Button>
              </div>
            </section>
          ) : (
            <section>
              <SignInForm />
              <div className="flex items-center justify-center mt-2 space-x-2">
                <span>Don't have an account?</span>
                <Button onClick={() => navigate("/signUp")} variant="ghost">
                  Sign Up
                </Button>
              </div>

              <div>
                <Button
                  className="w-full py-5 mt-4"
                  onClick={() => navigate("/forgotPass")}
                  variant="outline"
                >
                  Forgot Password
                </Button>
              </div>
            </section>
          )}
        </div>
      </div>
    </div>
  );
};

export default Auth;
