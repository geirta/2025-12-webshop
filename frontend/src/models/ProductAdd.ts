import type { Category } from "./Category"

export type ProductAdd = {
    name: string,
    description: string,
    price: number,
    stock: number,
    active: boolean,
    category: Category | null
}