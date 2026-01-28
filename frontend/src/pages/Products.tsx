import {useContext, useEffect, useState} from 'react'
import type { Product } from '../models/Product'
import { CartSumContext } from '../context/CartSumContext';
import type { CartProduct } from '../models/CartProduct';
import { increment } from '../store/counterSlice'
import { useAppDispatch } from '../store/store';
import { Link } from 'react-router-dom';


const Products = () => {

    const [products, setProducts] = useState<Product[]>([])
    const {increaseSum} = useContext(CartSumContext);
    const dispatch = useAppDispatch();
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(6);
    const [sort, setSort] = useState("id,asc");
    const [totalElements, setTotalElements] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        fetch(`http://localhost:8099/products?page=${page}&size=${size}&sort=${sort}`)
            .then(res => res.json())
            .then(json => {
                setProducts(json.content);
                setTotalElements(json.totalElements);
                setTotalPages(json.totalPages);
            })
    }, [page, size, sort]);

    function addToCart(productClicked: Product) {
        const cart: CartProduct[] = JSON.parse(localStorage.getItem("cart") || "[]");
        const cartProducts = cart.find(cp => cp.product.id === productClicked.id);
        if (cartProducts) {
            cartProducts.quantity++;
        } else {
            cart.push({product: productClicked, quantity: 1});
        }
        console.log(cartProducts);
        localStorage.setItem("cart", JSON.stringify(cart));
        increaseSum(productClicked.price);
        dispatch(increment());
    }
    
    /* LocalStorage-s on alati String väärtused
    // LocalStorage ei võimalda pushida/addida 
    // LocalStorage update+miseks on ainult .setItem
    
    // LocalStorage-sse array lisamiseks: 
    // 1. votta LS-st vana seis (LocalStorage.getItem)
    // 2. kui ei ole sellist v6tit LS-s, siis vota tuhi array (|| "[]")
    // 3. votta jutumargid maha (JSON.parse)
    // 4. lisa toode juurde (.push)
    // 5. pane jutumargid tagasi (JSON.stringify)
    // 6. pane LS-sse tagasi (LocalStorage.setItem)*/

    function updateSize(newSize: number) {
      setSize(newSize);
      setPage(0);
    }

    function updateSort(newSort: string) {
        setSort(newSort);
        setPage(0);
    }

    return (
        <div className='container'>
            <h2 className='text-center'>List of Products</h2>

            <div>Tooteid kokku: {totalElements}</div> 
            
            <br/><br/>

            Display products (qty): 
            <select onChange={e => updateSize(Number(e.target.value))} defaultValue={size}>
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
            </select>

            <br/><br/>

            <div>Sort</div>
            <button onClick={() => updateSort("id,asc")}>Vanemad ees</button>
            <button onClick={() => updateSort("id,desc")}>Uuemad ees</button>
            <button onClick={() => updateSort("name,asc")}>A-Z</button>
            <button onClick={() => updateSort("name,desc")}>Z-A</button>
            <button onClick={() => updateSort("price,asc")}>Hind kasvavalt</button>
            <button onClick={() => updateSort("price,desc")}>Hind kahanevalt</button>

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
                                <td><button onClick={() => addToCart(product)} className="btn btn-warning">Add to cart</button></td>
                                <td>
                                    <Link to={`/product/${product.id}`}>
                                        <button className='btn btn-dark'>Vt l2hemalt</button>
                                    </Link>
                                </td>
                            </tr>)
                    }
                </tbody>
            </table>
            <button disabled={page === 0} onClick={() => setPage(page - 1)}>Eelmine</button>
            <span>{page+1}</span>
            <button disabled={page+1 === totalPages} onClick={() => setPage(page + 1)}>Järgmine</button>
        </div>
    )
}

export default Products