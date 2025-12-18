import {useEffect, useState} from 'react'
import type { Product } from '../models/Product'

const ListProductsComponent = () => {

    const [products, setProducts] = useState<Product[]>([])

    useEffect(() => {
        fetch("http://localhost:8099/products")
            .then(res => res.json())
            .then(json => setProducts(json))
    }, []);

    return (
        <div className='container'>

            <h2 className='text-center'>List of Products</h2>
            <button className='btn btn-primary mb-2'>Add Product</button>
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
                                <td>{product.category.name}</td>
                                <td><button className="btn btn-warning">Add to cart</button></td>
                            </tr>)
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListProductsComponent