import {Descriptions} from "antd";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getRequest} from "../service/API_CONFIG";
import {EditOutlined, MailOutlined, UserOutlined} from "@ant-design/icons";

const UserPage = () => {
    const {id} = useParams();
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState('')
    const navigate = useNavigate()

    useEffect(() => {
        getRequest(`/users/${id}`)
            .then(r => {
                setFirstName(r.data.firstName)
                setLastName(r.data.lastName)
                setEmail(r.data.email)
            })
    }, [id]);

    return <div style={{
        display: "flex",
        flexDirection: "row",
        alignItems: "start",
        justifyContent: "start"
    }}>
        <Descriptions title="User Info" bordered layout={'horizontal'} column={1}>
            <Descriptions.Item label="First Name"><UserOutlined /> {firstName}</Descriptions.Item>
            <Descriptions.Item label="Last Name"><UserOutlined /> {lastName}</Descriptions.Item>
            <Descriptions.Item label="E-mail"><MailOutlined /> {email}</Descriptions.Item>
        </Descriptions>
        <EditOutlined style={{fontSize: '1.5rem', color: "gray", cursor: "pointer"}} onClick={() => navigate(`/user/${id}/edit`)}/>
    </div>
}

export default UserPage