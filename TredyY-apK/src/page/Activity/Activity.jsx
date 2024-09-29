import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Avatar, AvatarImage } from "@radix-ui/react-avatar";
const Activity = () => {
  return (
    <div className="p-5 lg:p-20">
      <h5 className="font-bold text-3xl pb-6">ACTIVITY</h5>
      <Table className="border">
        <TableHeader>
          <TableRow>
            <TableHead className="py-5">Date & Time</TableHead>
            <TableHead>Treading Pair</TableHead>
            <TableHead>Buy Price</TableHead>
            <TableHead>Sell Price</TableHead>
            <TableHead>Order Type</TableHead>
            <TableHead className="">Profit/Loss</TableHead>
            <TableHead className="text-right ">Value</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1].map((item, index) => (
            <TableRow key={index}>
            <TableCell>
            <p>2024/05/31</p>
            <p className="text-gray-500">12:39:32</p>
            </TableCell>
              <TableCell className="font-medium flex items-center gap-2">
                <Avatar className="w-6 h-6">
                  <AvatarImage src="https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400" />
                </Avatar>
                <span>Bitcoin</span>
              </TableCell>
              <TableCell className="">$57860.00</TableCell>
              <TableCell>1142871652369</TableCell>
              <TableCell>0.10164</TableCell>
              <TableCell className="">$57860.00</TableCell>
              <TableCell className="text-right">
                345
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  )
}

export default Activity
