import { Link } from "react-router-dom"

const backendUrl = import.meta.env.VITE_API_HOST;

function AdminHome() {



  return (
    <div>
      <Link to="/admin/manage-products">
        <button>Halda tooteid</button>
      </Link>
    </div>
  )
}

export default AdminHome
