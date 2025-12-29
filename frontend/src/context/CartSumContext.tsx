import { createContext } from "react";

// siin määran ära, mis väärtused mul on, mis aga kirjutatakse üle provideri poolt
export const CartSumContext = createContext({
  sum: 0,
  //setSum: (newSum: number) => {console.log(newSum)}
  increaseSum: (amount: number) => {console.log(amount)},
  decreaseSum: (amount: number) => {console.log(amount)},
  resetSum: () => {}
});
