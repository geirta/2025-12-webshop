import type { Category } from "./Category"

export type ProductCreate = {
    name: string,
    description: string,
    price: number,
    stock: number,
    active: boolean,
    category: Category | null
}