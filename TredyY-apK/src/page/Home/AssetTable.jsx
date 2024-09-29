import { ScrollArea } from "@/components/ui/scroll-area";
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
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

const AssetTable = ({ bitCoin, category }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  return (
    <Table>
    <ScrollArea className={`${category=='all'?'h-[77.3vh]':'h-[82vh]'}`}>
      <TableHeader>
        <TableRow>
          <TableHead className="w-[100px]">Bitcoin</TableHead>
          <TableHead>Symbol</TableHead>
          <TableHead>Volume</TableHead>
          <TableHead>Market Cap</TableHead>
          <TableHead>24h</TableHead>
          <TableHead className="text-right">Price</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {bitCoin.map((item, index) => (
          <TableRow key={item.id}>
            <TableCell
              onClick={() => navigate(`/market/${item.id}`)}
              className="font-medium flex items-center gap-2"
            >
              <Avatar className="w-8 h-8">
                <AvatarImage 
                  src={item.image} 
                  alt={item.name}
                  className="w-full h-full object-cover" 
                />
              </Avatar>
              <span className="ml-2 text-left">{item.name}</span>
            </TableCell>
            <TableCell>{item.symbol}</TableCell>
            <TableCell>{item.total_volume}</TableCell>
            <TableCell>{item.market_cap}</TableCell>
            <TableCell>{item.price_change_percentage_24h}</TableCell>
            <TableCell className="text-right">${item.current_price}</TableCell>
          </TableRow>
        ))}
      </TableBody>
      </ScrollArea>
    </Table>
  );
};

export default AssetTable;
