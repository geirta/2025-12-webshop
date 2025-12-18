import React, {useEffect, useState} from 'react'
import type { Person } from '../models/Person'
import { useNavigate } from 'react-router-dom'

const PersonsComponent = () => {

    const [persons, setPersons] = useState<Person[]>([])

    const navigator = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8099/persons")
            .then(res => res.json())
            .then(json => setPersons(json))
    }, []);

    function viewPerson(id: number) {
        navigator(`/persons/${id}`);
    }

    return (
        <div className='container'>

            <h2 className='text-center'>List of Persons</h2>
            <table className='table table-hover table-bordered'>
                <thead className='table-success'>
                    <tr>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Personal code</th>
                        <th>Phone</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        persons.map(person =>
                            <tr key={person.id} onClick={() => viewPerson(person.id)}>
                                <td>{person.id}</td>
                                <td>{person.firstName}</td>
                                <td>{person.email}</td>
                                <td>{person.lastName}</td>
                                <td>{person.personalCode}</td>
                                <td>{person.phone}</td>
                            </tr>)
                    }
                </tbody>
            </table>
        </div>
    )
}

export default PersonsComponent