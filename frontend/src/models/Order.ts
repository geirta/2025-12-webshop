import type { CartProduct } from "./CartProduct"
import type { Person } from "./Person"

export type Order = {
    id: number,
    total: number,
    person: Person,
    paymentStatus: string,
    orderRows: CartProduct[]
}