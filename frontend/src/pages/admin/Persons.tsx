//import {useEffect, useState} from 'react'
import type { Person } from '../../models/Person'
import { useNavigate } from 'react-router-dom'
import { useFetch } from '../../hooks/useFetch';
import { useEffect, useState } from 'react';

const backendUrl = import.meta.env.VITE_API_HOST;

const Persons = () => {

    const [persons, setPersons] = useState<Person[]>([]);
    const dbPersons = useFetch<Person>({endpoint: "persons"});
    
    const navigator = useNavigate();

    function viewPerson(id: number) {
        navigator(`/persons/${id}`);
    }

    useEffect(() => {
        setPersons(dbPersons);
    }, [dbPersons]);

    const changeAdmin = async (person: Person) => {
        const res = await fetch(backendUrl + "/change-admin" + "?personId=" + person.id, {
            "method": "PATCH",
            "headers": {
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            }
        });
        const json = await res.json();
        setPersons(json);
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
                        <th>Change admin</th>
                        <th>Profiil</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        persons.map(person =>
                            <tr key={person.id} >
                                <td>{person.id}</td>
                                <td>{person.firstName}</td>
                                <td>{person.lastName}</td>
                                <td>{person.email}</td>
                                <td>{person.personalCode}</td>
                                <td>{person.phone}</td>
                                <td>
                                    {person.role === "SUPERADMIN" 
                                        ? <span>SUPERADMIN</span>
                                        : <button onClick={() => changeAdmin(person)}>{person.role}</button>
                                    }
                                    
                                </td>

                                <td>
                                    <button onClick={() => viewPerson(person.id)}>Muuda</button>
                                </td>
                            </tr>)
                    }
                </tbody>
            </table>
        </div>
    )
}

export default Persons