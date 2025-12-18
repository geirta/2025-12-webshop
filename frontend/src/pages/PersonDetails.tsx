import React, {useEffect, useState} from 'react'
import type { Person } from '../models/Person'
import { useParams } from 'react-router-dom';

const backendUrl = import.meta.env.VITE_API_HOST;

const PersonDetails = () => {

    const {id} = useParams(); // gets id from Url
    const [person, setPerson] = useState<Person|null>(null);
    const [isEditing, setIsEditing] = useState(false)
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [personalCode, setPersonalCode] = useState("");
    const [phone, setPhone] = useState("");
    
    const [form, setForm] = useState({
        firstName: "",
        lastName: "",
        email: "",
        personalCode: "",
        phone: ""
    });

    const handleSave = (e: React.FormEvent) => {
        e.preventDefault();

        fetch(`${backendUrl}/persons/${id}`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                firstName,
                lastName,
                email,
                personalCode,
                phone
            })
        })
            .then(res => res.json())
            .then(updated => {
                setPerson(updated);
                setIsEditing(false);
            });
    };

    const cancelEdit = () => {
        setForm({
            firstName: person!.firstName,
            lastName: person!.lastName,
            email: person!.email,
            personalCode: person!.personalCode,
            phone: person!.phone
        });
        setIsEditing(false);
    };

   useEffect(() => {
        if (!id) return;
        fetch(`${backendUrl}/persons/${id}`)
            .then(res => res.json())
            .then(json => {
                setPerson(json);
                setFirstName(json.firstName);
                setLastName(json.lastName);
                setEmail(json.email);
                setPersonalCode(json.personalCode);
                setPhone(json.phone);
            });
    }, [id]);

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
                                <input type="text" id="fname" value={firstName} className='dataField'
                                    disabled={!isEditing} onChange={e => setFirstName(e.target.value)}>
                                </input>
                            </div>
                            <div>
                                <label>Last name:</label><br></br>
                                <input type="text" id="lname" value={lastName} className='dataField' 
                                    disabled={!isEditing} onChange={e => setLastName(e.target.value)}>
                                </input>
                            </div>
                            <div>
                                <label>Email:</label><br></br>
                                <input type="text" id="email" value={email} className='dataField'
                                    disabled={!isEditing} onChange={e => setEmail(e.target.value)}>
                                </input>
                            </div>
                            <div>
                                <label>Personal code:</label><br></br>
                                <input type="text" id="personalCode" value={personalCode} className='dataField'
                                    disabled={!isEditing} onChange={e => setPersonalCode(e.target.value)}>
                                </input>
                            </div>
                            <div>
                                <label>Phone</label><br></br>
                                <input type="text" id="phone" value={phone} className='dataField'
                                    disabled={!isEditing} onChange={e => setPhone(e.target.value)}>
                                </input>
                            </div>
                        </div>
                        <div className='col'>
                            <h2 className='text-center'>Address</h2>
                            <div>
                                <label>City:</label><br></br>
                                <input type="text" id="city" value={person.address.city} className='dataField' disabled={!isEditing}></input>
                            </div>
                            <div>
                                <label>State:</label><br></br>
                                <input type="text" id="state" value={person.address.state} className='dataField' disabled={!isEditing}></input>
                            </div>
                            <div>
                                <label>Country:</label><br></br>
                                <input type="text" id="country" value={person.address.country} className='dataField' disabled={!isEditing}></input>
                            </div>
                            <div>
                                <label>Zipcode:</label><br></br>
                                <input type="text" id="zipcode" value={person.address.zipcode} className='dataField' disabled={!isEditing}></input>
                            </div>
                            <div>
                                <label>Street</label><br></br>
                                <input type="text" id="street" value={person.address.street} className='dataField' disabled={!isEditing}></input>
                            </div>
                            <div>
                                <label>Number</label><br></br>
                                <input type="text" id="houseNr" value={person.address.number} className='dataField' disabled={!isEditing}></input>
                            </div>
                            <div>
                                <label>Complement</label><br></br>
                                <input type="text" id="complement" value={person.address.complement} className='dataField' disabled={!isEditing}></input>
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