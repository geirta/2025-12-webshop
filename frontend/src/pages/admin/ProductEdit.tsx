import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type { Product } from "../../models/Product";
import type { Category } from "../../models/Category";
import { useFetch } from "../../hooks/useFetch";

const backendUrl = import.meta.env.VITE_API_HOST;

function ProductEdit() {
  // <Route path='/product/:product_id' element={<ProductDetails />}></Route>
  const {product_id} = useParams();
  const [product, setProduct] = useState<Product>();
  // const [categories, setCategories] = useState<Category[]>([]);

  // useEffect(() => {
  //     fetch(`${backendUrl}/categories`)
  //         .then(res => res.json())
  //         .then(json => setCategories(json))
  // }, []);

  const categories = useFetch<Category>({endpoint: "categories"});

  useEffect(() => {
      fetch(`${backendUrl}/products/${product_id}`)
          .then(res => res.json())
          .then(json => setProduct(json))
  }, [product_id]);

  if (product === undefined) {
    return <div>Product not found</div>
  }

  return (
    <div>
      <div>Ajutine kuvamine: {JSON.stringify(product)}</div>
      <label>ID</label><br/>
      <input value={product.id} disabled type="text"></input><br/>
      <label>Name</label><br/>
      <input value={product.name} onChange={(e) => setProduct({...product, name: e.target.value})} type="text"></input><br/>
      <label>Price</label><br/>
      <input value={product.price} onChange={(e) => setProduct({...product, price: Number(e.target.value)})} type="number"></input><br/>
      <label>Stock</label><br/>
      <input value={product.stock} onChange={(e) => setProduct({...product, stock: Number(e.target.value)})} type="number"></input><br/>
      <label>Active</label><br/>
      <input checked={product.active} onChange={(e) => setProduct({...product, active: e.target.checked})} type="checkbox"></input><br/>
      <label>Category</label><br/>
      {/* <input value={product.category} onChange={(e) => setProduct({...product, category: e.target.value})}></input><br/> */}

      <select value={product.category?.id}
        onChange={(e) => setProduct({...product, category: {id: Number(e.target.value), name: ""}})}>
        {categories.map(category =>
          <option key={category.id} value={category.id}>
            {category.name}
          </option>
        )}
      </select>
      <button>Edit</button>
    </div>
  )
}

export default ProductEdit
