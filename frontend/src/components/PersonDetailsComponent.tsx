import React, {useEffect, useState} from 'react'
import type { Person } from '../models/Person'
import { useParams } from 'react-router-dom';

const PersonComponent = () => {

    const {id} = useParams(); // gets id from Url
    const [person, setPerson] = useState<Person|null>(null);

   
    useEffect(() => {
        if (!id) return;
        fetch(`http://localhost:8099/persons/${id}`)
            .then(res => res.json())
            .then(json => setPerson(json))
    }, [id]);

    if (!person) {
        return <div className="container">Loading...</div>;
    }

    return (
        <div className='container'>
        
    
            <form>
                <div className='container'>
                    <div className='row'>
                        <div className='col'>
                            <h2 className='text-center'>Person Details</h2>
                            <div>
                                <label>First name:</label><br></br>
                                <input type="text" id="fname" value={person.firstName}></input>
                            </div>
                            <div>
                                <label>Last name:</label><br></br>
                                <input type="text" id="lname" value={person.lastName}></input>
                            </div>
                            <div>
                                <label>Email:</label><br></br>
                                <input type="text" id="email" value={person.email}></input>
                            </div>
                            <div>
                                <label>Personal code:</label><br></br>
                                <input type="text" id="personalCode" value={person.personalCode}></input>
                            </div>
                            <div>
                                <label>Phone</label><br></br>
                                <input type="text" id="phone" value={person.phone}></input>
                            </div>
                        </div>
                        <div className='col'>
                            <h2 className='text-center'>Address</h2>
                            <div>
                                <label>City:</label><br></br>
                                <input type="text" id="city" value={person.address.city}></input>
                            </div>
                            <div>
                                <label>State:</label><br></br>
                                <input type="text" id="state" value={person.address.state}></input>
                            </div>
                            <div>
                                <label>Country:</label><br></br>
                                <input type="text" id="country" value={person.address.country}></input>
                            </div>
                            <div>
                                <label>Zipcode:</label><br></br>
                                <input type="text" id="zipcode" value={person.address.zipcode}></input>
                            </div>
                            <div>
                                <label>Street</label><br></br>
                                <input type="text" id="street" value={person.address.street}></input>
                            </div>
                            <div>
                                <label>Number</label><br></br>
                                <input type="text" id="houseNr" value={person.address.number}></input>
                            </div>
                            <div>
                                <label>Complement</label><br></br>
                                <input type="text" id="complement" value={person.address.complement}></input>
                            </div>
                        </div>
                    </div>
                    
                </div>
                
                <button type="submit" className="btn btn-primary">Save</button>

            </form>
        </div>
    )
}

export default PersonComponent