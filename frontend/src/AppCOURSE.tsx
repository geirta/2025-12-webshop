import { useEffect, useState } from 'react'
import './App.css'
import type { Product } from './models/Product';
import type { Category } from './models/Category';

function App() {

  // useState --> Reacti erikood (hook)
  // useState väljastab array: esimene väärtus on muutuja, teine on setter
  // setteril on eriomadused - kui see käivitatakse, siis muutub muutuja + renderdatakse HTML uuesti

  //const [count, setCount] = useState(1);
  //const [count2, setCount2] = useState(10); // väärtused, mida rohkem ei muudeta

  const [products, setProducts] = useState<Product[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  
  // ERR?CONNECTION_REFUSED -->> backend ei tööta

  // fetch(URL) --> kuhu teise serverisse ma pöördun
  // res ---> KOGU TAGASTUS. headers, statusCode, jne
  // res.json ---> mis kujul on returnitav väärtus(json) .. res.text() -> string
  // json ---> mida see äpi endpoint returnib

  

  // variant 2
  // async function getProducts() {
  //   const res = await fetch("http://localhost:8099/products");
  //   const json = await res.json();
  //   console.log(json); // setProducts(json)
  // }
  // getProducts();

  // VANA REACT: useComponentDidMount   useComponentWillMount
  useEffect(() => {
    // variant 1
    fetch("http://localhost:8099/products")
      .then(res => res.json())
      .then(json => setProducts(json)) // setProducts(json)
  }, []);

  useEffect(() => {
  fetch("http://localhost:8099/categories")
    .then(res => res.json())
    .then(json => setCategories(json)) // setProducts(json)
  }, []);


  // KOJU: Categories sama asi
  // uus useState
  // CrossOrigin
  // uus useEffect
  // uus mudel

  // Rendipood / kümnevõistlus

  return (
    <>
      {products.map(product => 
        <div key={product.id}>
          <div>{product.name}</div>
          <div>{product.price}€</div>
        </div>)}

      {categories.map(category => 
        <div key={category.id}>
          <div>{category.name}</div>
        </div>
      )}


    {/*}
      <div>{count}</div>
      <button onClick={() => setCount(prev => prev + 1)}>Suurenda</button>

      <div>{count2}</div>
      <button onClick={() => setCount2(prev => prev + 1)}>Suurenda</button>
      */}
    </>
  )
}

export default App
