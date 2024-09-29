import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { VerifiedIcon } from "lucide-react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import AccountVerificationForm from "./AccountVerificationForm";
import { useSelector } from "react-redux";
import { store } from "@/state/Store";

const Profile = () => {

  const {auth} = useSelector(store=>store)

  const handleEnableTwoStepVerification = () => {
    console.log("2 step verification enabled...");
  };

  return (
    <div className="flex flex-col items-center mb-5">
      <div className="pt-10 w-full lg:w-[60%]">
        <Card>
          <CardHeader className="pb-9">
            <CardTitle>Your Information</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
              <div className="space-y-7">
                <div className="flex">
                  <p className="w-[9rem] font-semibold">Email :</p>
                  <p className="text-grey-500">{auth.user?.email}</p>
                </div>
                <div className="flex">
                  <p className="w-[9rem] font-semibold">Full Name :</p>
                  <p className="text-grey-500">{auth.user?.fullName}</p>
                </div>
                <div className="flex">
                  <p className="w-[9rem] font-semibold">Date Of Birth :</p>
                  <p className="text-grey-500">17/04/2000</p>
                </div>
                <div className="flex">
                  <p className="w-[9rem] font-semibold">Nationality :</p>
                  <p className="text-grey-500">Indian</p>
                </div>
              </div>
              <div className="space-y-7">
                <div className="flex">
                  <p className="w-[9rem] font-semibold">Email :</p>
                  <p className="text-grey-500">{auth.user?.email}</p>
                </div>
                <div className="flex">
                  <p className="w-[9rem] font-semibold">Full Name :</p>
                  <p className="text-grey-500">{auth.user?.fullName}</p>
                </div>
                <div className="flex">
                  <p className="w-[9rem] font-semibold">Date Of Birth :</p>
                  <p className="text-grey-500">17/04/2000</p>
                </div>
                <div className="flex">
                  <p className="w-[9rem] font-semibold">Nationality :</p>
                  <p className="text-grey-500">Indian</p>
                </div>
              </div>
            </div>
          </CardContent>
        </Card>
        <div className="mt-6">
          <Card className="w-full">
            <CardHeader className="pb-7">
              <div className="flex items-center gap-3">
                <CardTitle>2 step Verification</CardTitle>
                {true ? (
                  <Badge className={"space-x-2 text-white bg-green-600"}>
                    <VerifiedIcon />
                    <span>Enabled</span>
                  </Badge>
                ) : (
                  <Badge className="bg-orange-500">Disabled</Badge>
                )}
              </div>
            </CardHeader>
            <CardContent>
              <div>
                <Dialog>
                  <DialogTrigger>
                    <Button>Enabled 2 Factor Authentication</Button>
                  </DialogTrigger>
                  <DialogContent>
                    <DialogHeader>
                      <DialogTitle>Verify Your Account</DialogTitle>
                      <DialogDescription>
                        This action cannot be undone. This will permanently
                        delete your account and remove your data from our
                        servers.
                      </DialogDescription>
                    </DialogHeader>
                    <AccountVerificationForm
                      handleSubmit={handleEnableTwoStepVerification}
                    />
                  </DialogContent>
                </Dialog>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default Profile;
