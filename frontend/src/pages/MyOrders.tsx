// import { useEffect, useState } from 'react'
import type { Order } from '../models/Order';
import { useFetch } from '../hooks/useFetch';

// const backendUrl = import.meta.env.VITE_API_HOST;

function MyOrders() {

  // const [orders, setOrders] = useState<Order[]>([])

  // useEffect(() => {
  //     fetch(`${backendUrl}/person-orders`, {
  //         headers: {
  //             "Authorization": "Bearer " + sessionStorage.getItem("token")
  //         }
  //     })
  //         .then(res => res.json())
  //         .then(json => {
  //             setOrders(json);
  //         });
  // }, []);

  const orders = useFetch<Order>({endpoint: "person-orders"});

  return (
    <div>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Total</th>
              <th>Payment status</th>
              <th>Products</th>
            </tr>
          </thead>
          <tbody>
            {
              orders.map(order => 
                <tr key={order.id}>
                  <td>{order.id}</td>
                  <td>{order.total}</td>
                  <td>{order.paymentStatus}</td>
                </tr>
              )
            }
          </tbody>
        </table>

    </div>
  )
}

export default MyOrders
