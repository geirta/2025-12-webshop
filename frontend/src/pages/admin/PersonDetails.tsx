import React, {useEffect, useState} from 'react'
import type { Person } from '../../models/Person'
import { useParams } from 'react-router-dom';

const backendUrl = import.meta.env.VITE_API_HOST;

const PersonDetails = () => {

    const {id} = useParams(); // gets id from Url
    const [person, setPerson] = useState<Person|null>(null);
    const [originalPerson, setOriginalPerson] = useState<Person|null>(null);
    const [isEditing, setIsEditing] = useState(false)
    
   useEffect(() => {
        //if (!id) return;

        fetch(`${backendUrl}/persons/${id}`, {
            headers: {
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            }
        })
            .then(res => res.json())
            .then(json => {
                setPerson(json);
                setOriginalPerson(json);
            });
    }, [id]);


    const handleSave = (e: React.FormEvent) => {
        e.preventDefault();

        fetch(`${backendUrl}/persons`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            },
            body: JSON.stringify(person)
        })
            .then(res => res.json())
            .then(updated => {
                setPerson(updated);
                setIsEditing(false);
            });
    };

    const cancelEdit = () => {
        setIsEditing(false);
        setPerson(originalPerson);
    };


    if (!person) {
        return <div className="container">Loading...</div>;
    }

    return (
        <div className='container'>
            <form onSubmit={handleSave}>
                <div className='container'>
                    <div className='row'>
                        <div className='col'>
                            <h2 className='text-center'>Person Details</h2>
                            <div>
                                <label>First name:</label><br></br>
                                <input type="text" id="fname" value={person.firstName} className='dataField'
                                    disabled={!isEditing} onChange={e => setPerson({...person, firstName: e.target.value})}>
                                </input>
                            </div>
                            <div>
                                <label>Last name:</label><br></br>
                                <input type="text" id="lname" value={person.lastName} className='dataField' 
                                    disabled={!isEditing} onChange={e => setPerson({...person, lastName: e.target.value})}>
                                </input>
                            </div>
                            <div>
                                <label>Email:</label><br></br>
                                <input type="text" id="email" value={person.email} className='dataField'
                                    disabled={!isEditing} onChange={e => setPerson({...person, "email": e.target.value})}>
                                </input>
                            </div>
                            <div>
                                <label>Personal code:</label><br></br>
                                <input type="text" id="personalCode" value={person.personalCode} className='dataField'
                                    disabled={!isEditing} onChange={e => setPerson({...person, "personalCode": e.target.value})}>
                                </input>
                            </div>
                            <div>
                                <label>Phone</label><br></br>
                                <input type="text" id="phone" value={person.phone} className='dataField'
                                    disabled={!isEditing} onChange={e => setPerson({...person, "phone": e.target.value})}>
                                </input>
                            </div>
                        </div>
                        <div className='col'>
                            <h2 className='text-center'>Address</h2>
                            <div>
                                <label id="city">City:</label><br></br>
                                <input type="text" id="city" value={person.address?.city} className='dataField' disabled={!isEditing} 
                                    onChange={(e) => setPerson({...person, address: {...person.address, city: e.target.value}})}
                                />
                            </div>
                            <div>
                                <label>State:</label><br></br>
                                <input type="text" id="state" value={person.address?.state} className='dataField' disabled={!isEditing} 
                                    onChange={(e) => setPerson({...person, address: {...person.address, state: e.target.value}})}
                                />
                            </div>
                            <div>
                                <label>Country:</label><br></br>
                                <input type="text" id="country" value={person.address?.country} className='dataField' disabled={!isEditing}
                                    onChange={(e) => setPerson({...person, address: {...person.address, country: e.target.value}})}
                                />
                            </div>
                            <div>
                                <label>Zipcode:</label><br></br>
                                <input type="text" id="zipcode" value={person.address?.zipcode} className='dataField' disabled={!isEditing}
                                    onChange={(e) => setPerson({...person, address: {...person.address, zipcode: e.target.value}})}
                                />
                            </div>
                            <div>
                                <label>Street</label><br></br>
                                <input type="text" id="street" value={person.address?.street} className='dataField' disabled={!isEditing}
                                    onChange={(e) => setPerson({...person, address: {...person.address, street: e.target.value}})}
                                />
                            </div>
                            <div>
                                <label>Number</label><br></br>
                                <input type="text" id="houseNr" value={person.address?.number} className='dataField' disabled={!isEditing}
                                    onChange={(e) => setPerson({...person, address: {...person.address, number: e.target.value}})}
                                />
                            </div>
                            <div>
                                <label>Complement</label><br></br>
                                <input type="text" id="complement" value={person.address?.complement} className='dataField' disabled={!isEditing} 
                                    onChange={(e) => setPerson({...person, address: {...person.address, complement: e.target.value}})}
                                />
                            </div>
                        </div>
                    </div>
                    <div className='row'>
                        <div className='col'>
                            <button type="button" className="btn btn-light" 
                                onClick={() => isEditing ? cancelEdit() : setIsEditing(true)}
                            >
                                    {isEditing ? "Cancel" : "Edit"}
                            </button>
                        <button type="submit" className="btn btn-primary" disabled={!isEditing}>Save</button>
                        </div>
                        <div className='col'>
                            
                        </div>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default PersonDetails