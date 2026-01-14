import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom"

const backendUrl = import.meta.env.VITE_API_HOST;

function CheckPayment() {

  // useParams() :id      useSearchParams ?payment_reference
  const [searchParams] = useSearchParams();
  const [isPaid, setIsPaid] = useState(false);
  const [loading, setLoading] = useState(true);
  const order_reference = searchParams.get("order_reference");
  const payment_reference = searchParams.get("payment_reference");

  useEffect(() => {
    fetch(backendUrl + `/check-payment?orderReference=${order_reference}&paymentReference=${payment_reference}`)
      .then(res => res.json())
      .then(json => {
        setIsPaid(json.paid);
        setLoading(false)
      })
  }, [order_reference, payment_reference]);

  if (loading) {
    return <div>Loading...</div>
  }

  return (
    <div>
      {isPaid 
        ? <div>Tellimus id-ga {order_reference} edukalt makstud</div>
        : <div>Tellimus id-ga {order_reference} jäi kahjuks maksmata</div>
      }
    </div>
  )
}

export default CheckPayment
