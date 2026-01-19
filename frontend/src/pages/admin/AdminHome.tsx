import { Link } from "react-router-dom"

const backendUrl = import.meta.env.VITE_API_HOST;

function AdminHome() {



  return (
    <div>
      <Link to="/admin/add-products">
        <button>Lisa tooteid</button>
      </Link>
      <Link to="/admin/manage-products">
        <button>Halda tooteid</button>
      </Link>
      <Link to="/admin/manage-categories">
        <button>Halda kategooriaid</button>
      </Link>
    </div>
  )
}

export default AdminHome
