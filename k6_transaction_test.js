import http from 'k6/http';
import { uuidv4 } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';


export const options = {
  thresholds: {
    // Assert that 99% of requests finish within 3000ms.
    http_req_duration: ["p(99) < 3000"],
  },
  // Ramp the number of virtual users up and down


  vus: 5,
  duration: '40s'
};


export default function () {
  const url = 'http://localhost:8069/transaction/charge';


  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  http.post(url, JSON.stringify({
    transaction_id : uuidv4(),
    transaction_value : Math.floor((Math.random() * 100) + 1),
    transaction_date: "2024-03-05T21:19:26.492745400",
    saga_workflow_name : 'BUSINESS_PAYMENT'
  }), params);
}