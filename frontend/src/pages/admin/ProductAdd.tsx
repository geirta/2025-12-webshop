import { useState } from "react";
import type { Category } from "../../models/Category";
import { useFetch } from "../../hooks/useFetch";
import type { ProductCreate } from "../../models/ProductCreate";

const backendUrl = import.meta.env.VITE_API_HOST;

function ProductAdd() {

  const emptyProduct: ProductCreate = {
    name: "",
    description: "",
    price: 0,
    stock: 0,
    active: false,
    category: null
  };

  const [product, setProduct] = useState<ProductCreate>(emptyProduct);

  const categories = useFetch<Category>({endpoint: "categories"});

  function addProduct() {
    fetch(backendUrl + "/products", {
      method: "POST",
      body: JSON.stringify(product),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())
      .then(json => {
        console.log(json);
        setProduct(emptyProduct);
      });
  }

  return (
    <div className='container'>
      <div className='container'>
        <div className='row'>
          <div className='col'>
            <h2 className='text-center'>Add new product</h2>
            <div>
              <label>Product name:</label><br></br>
              <input type="text" value={product.name} className='dataField'
                  onChange={e => setProduct({...product, name: e.target.value})}>
              </input>
            </div>
            <div>
              <label>Description:</label><br></br>
              <input type="text" value={product.description} className='dataField' 
                  onChange={e => setProduct({...product, description: e.target.value})}>
              </input>
            </div>
            <div>
              <label>Price:</label><br></br>
              <input type="number" value={product.price} className='dataField'
                  onChange={e => setProduct({...product, price: Number(e.target.value)})}>
              </input>
            </div>
            <div>
              <label>Stock:</label><br></br>
              <input type="number" value={product.stock} className='dataField'
                  onChange={e => setProduct({...product, stock: Number(e.target.value)})}>
              </input>
            </div>
            <div>
              <label>Active</label><br></br>
              <input type="checkbox" checked={product.active} className='dataField'
                  onChange={e => setProduct({...product, active: e.target.checked})}>
              </input>
            </div>
            <div>
              <label>Category</label>
              <select value={product.category?.id}
                onChange={(e) => setProduct({...product, category: {id: Number(e.target.value), name: ""}})}>
                <option value="">Select category</option>
                {categories.map((category: Category) =>
                  <option key={category.id} value={category.id}>
                    {category.name}
                  </option>
                )}
              </select>
            </div>
          </div>
        </div>
        <div className='row'>
          <div className='col'>
            <button type="submit" className="btn btn-primary" onClick={() => addProduct()}>Add product</button>
          </div>
        </div>
      </div>
  </div>
  )
}

export default ProductAdd
