import { fetchMarketChart } from "@/state/Bitcoin/Action";
import React, { useEffect, useState } from "react";
import ReactApexChart from "react-apexcharts";
import { useDispatch, useSelector } from "react-redux";
import { Button } from "@/components/ui/button";

const timeSeries = [
  {
    keyword: "DIGITAL_CURRENCY_DAILY",
    key: "Daily Time Series",
    label: "1 Day",
    value: 1,
  },
  {
    keyword: "DIGITAL_CURRENCY_WEEKLY",
    key: "Weekly Time Series",
    label: "1 Week",
    value: 7,
  },
  {
    keyword: "DIGITAL_CURRENCY_MONTHLY",
    key: "Monthly Time Series",
    label: "1 Month",
    value: 30,
  },
  {
    keyword: "DIGITAL_CURRENCY_YEARLY",
    key: "Yearly Time Series",
    label: "1 Year",
    value: 30,
  },
];

const Stockchart = ({ bitcoinId }) => {
  const dispatch = useDispatch();
  const marketChartData = useSelector((state) => state.bitCoin.marketChart.data); // Access only the market chart data

  const [activeLabel, setActiveLabel] = useState(timeSeries[0]);

  const series = [
    {
      data: marketChartData || [], // Ensure that there's data or an empty array
    },
  ];

  const options = {
    chart: {
      id: "area-datetime",
      type: "area",
      height: 350,
      zoom: {
        autoScaleYaxis: true,
      },
    },
    dataLabels: {
      enabled: false,
    },
    xaxis: {
      type: "datetime",
      tickAmount: 6,
    },
    colors: ["#758AA2"],
    markers: {
      colors: ["#fff"],
      strokeColor: "#fff",
      strokeOpacity: 0.2,
      strokeWidth: 1,
      style: "hollow",
    },
    tooltip: {
      theme: "dark",
    },
    fill: {
      type: "gradient",
      gradient: {
        shadeIntensity: 1,
        opacityFrom: 0.8,
        opacityTo: 0.9,
        stops: [0, 100],
      },
    },
    grid: {
      borderColor: "#47535E",
      strokeDashArray: 4,
      show: true,
    },
  };

  const handleActiveLabel = (value) => {
    setActiveLabel(value);
  };

  useEffect(() => {
    dispatch(fetchMarketChart({ bitcoinId, days: activeLabel.value, jwt: localStorage.getItem('jwt') }));
  }, [dispatch, bitcoinId, activeLabel]);

  return (
    <div>
      <div className="space-x-3">
        {timeSeries.map((item) => (
          <Button
            key={item.label}
            variant={activeLabel.label === item.label ? "" : "outline"}
            onClick={() => handleActiveLabel(item)}
          >
            {item.label}
          </Button>
        ))}
      </div>
      <div id="chart-timelines">
        <ReactApexChart
          options={options}
          series={series}
          height={450}
          type="area"
        />
      </div>
    </div>
  );
};

export default Stockchart;
