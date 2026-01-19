//import { useEffect, useState } from "react";
import { useEffect, useState } from "react";
import { useFetch } from "../../hooks/useFetch";
import type { Category } from "../../models/Category";

const backendUrl = import.meta.env.VITE_API_HOST;

function CategoriesManage() {

  const [categories, setCategories] = useState<Category[]>([]);
  const dbCategories = useFetch<Category>({endpoint: "categories"});
  const [category, setCategory] = useState({"name": ""})
  
  useEffect(() => {
    setCategories(dbCategories);
  }, [dbCategories]);

  function addCategory() {
    fetch(backendUrl + "/categories", {
      method: "POST",
      body: JSON.stringify(category),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())
      .then(json => setCategories(json));
  }

  return (
    <div className='container'>
      <h2 className='text-center'>List of Categories</h2>
      <label>Category</label>
      <input onChange={e => setCategory({"name": e.target.value})} type="text"></input>
      <button onClick={() => addCategory()}>Add category</button>
      <table className='table table-striped table-bordered'>
        <thead className='table-success'>
          <tr>
            <th>ID</th>
            <th>Name</th>
          </tr>
        </thead>
        <tbody>
          {
            categories.map(category =>
              <tr key={category.id}>
                  <td>{category.id}</td>
                  <td>{category.name}</td>
              </tr>)
            }
        </tbody>
      </table>
    </div>
  )
}

export default CategoriesManage
