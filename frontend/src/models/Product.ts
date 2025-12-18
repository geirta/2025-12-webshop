export type Product = {
    id: number,
    name: string,
    price: number,
    stock: number,
    active: boolean,
    category: {
        id: number,
        name: string
    }
}