// util sees on funktsioonid, mis ei tagasta HTMLi

import type { CartProduct } from "../models/CartProduct";

// components/alamkomponent.tsx <-- tagastab HTMLi

// kui on vaja kasutada hooki: useState v6i useEffect ja tahan taaskasutada

export function calculateCartSum(cartProducts: CartProduct[]) {
  let sum = 0;
  cartProducts.forEach(cp => {
    sum += cp.product.price * cp.quantity;
  });
  return sum;
}