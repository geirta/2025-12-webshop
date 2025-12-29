import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import type { Product } from "../../models/Product";

const backendUrl = import.meta.env.VITE_API_HOST;

function ProductManage() {

  const [products, setProducts] = useState<Product[]>([])

  useEffect(() => {
      fetch(`${backendUrl}/admin-products`)
          .then(res => res.json())
          .then(json => setProducts(json))
  }, []);

  return (
    <div className='container'>
        <h2 className='text-center'>List of Products</h2>
        <Link to="/admin/add-product">
          <button className='btn btn-primary mb-2'>Add Product</button>
        </Link>
        <table className='table table-striped table-bordered'>
          <thead className='table-success'>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Price</th>
              <th>Stock</th>
              <th>Active</th>
              <th>Category</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {
              products.map(product =>
                <tr key={product.id}>
                    <td>{product.id}</td>
                    <td>{product.name}</td>
                    <td>{product.price}</td>
                    <td>{product.stock}</td>
                    <td>{product.active.toString()}</td>
                    <td>{product.category?.name}</td>
                    <td>
                      <Link to={`/admin/edit-product/${product.id}`}>
                          <button>Muuda</button>
                      </Link>
                    </td>
                </tr>)
              }
          </tbody>
      </table>
    </div>
  )
}

export default ProductManage
