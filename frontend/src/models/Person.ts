export type Person = {
    id: number,
    firstName: string,
    lastName: string,
    email: string,
    personalCode: string,
    phone: string,
    address: {
        id: number,
        city: string,
        state: string,
        country: string,
        zipcode: string,
        street: string,
        number: string,
        complement: string
    }
}