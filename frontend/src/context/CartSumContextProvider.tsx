import { useState, type ReactNode } from "react";
import { CartSumContext } from "./CartSumContext";
import type { CartProduct } from "../models/CartProduct";

// provider abil määran, milline component saab kätte
export const CartSumContextProvider = ({children}: {children: ReactNode}) => {
  
  const [sum, setSum] = useState(calculateCartSum());

  function decreaseSum(amount: number) {
    setSum(sum - amount);
  }

  function increaseSum(amount: number) {
    setSum(sum + amount);
  }

  function resetSum() {
    setSum(0);
  }

  function calculateCartSum() {
    const cart: CartProduct[] = JSON.parse(localStorage.getItem("cart") || "[]");
    let sum = 0;
    cart.forEach(cp => sum += cp.product.price * cp.quantity);
    return sum;
  }

  return (
    <CartSumContext.Provider value={{sum, decreaseSum, increaseSum, resetSum}}>
      {children}
    </CartSumContext.Provider>
  )
}