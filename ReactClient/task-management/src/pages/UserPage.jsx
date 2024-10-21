import {Badge, Descriptions} from "antd";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getRequest} from "../service/API_CONFIG";
import {MailOutlined, UserOutlined} from "@ant-design/icons";

const UserPage = () => {
    const {id} = useParams();
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState('')

    useEffect(() => {
        getRequest(`/users/${id}`)
            .then(r => {
                setFirstName(r.data.firstName)
                setLastName(r.data.lastName)
                setEmail(r.data.email)
            })
    }, []);

    return <>
        <Descriptions title="User Info" bordered layout={'horizontal'} column={1}>
            <Descriptions.Item label="First Name"><UserOutlined /> {firstName}</Descriptions.Item>
            <Descriptions.Item label="Last Name"><UserOutlined /> {lastName}</Descriptions.Item>
            <Descriptions.Item label="E-mail"><MailOutlined /> {email}</Descriptions.Item>
        </Descriptions>
    </>
}

export default UserPage