import React, {useEffect, useState} from "react";
import {deleteRequest, getRequest} from "../service/API_CONFIG";
import Meta from "antd/es/card/Meta";
import {Avatar, Button, Card, Col, Form, Input, message, Popconfirm, Row, Space, Tooltip} from "antd";
import {CloseOutlined, EditOutlined} from "@ant-design/icons";
import getRandomPastelColor, {getInitials} from "../utils/Utils";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const UsersPage = () => {
    const [users, setUsers] = useState([])
    const [form] = Form.useForm();
    const navigate = useNavigate()


    useEffect(() => {
        getRequest('/users/all').then(r => {
            setUsers(r.data)
        });
    }, []);

    const getAvatar = (user) => {
        const initials = getInitials(user.firstName, user.lastName);
        const randomColor = getRandomPastelColor();
        return (
            <Tooltip title={`${user.firstName} ${user.lastName}`} placement="top" key={user.id}>
                <Avatar style={{backgroundColor: randomColor}}>
                    {initials}
                </Avatar>
            </Tooltip>
        );
    }

    const getFields = () => {
        const children = [];
        children.push(
            <Row>
                <Form.Item
                    name={`firstName`}
                    rules={[
                        {
                            required: false,
                        },
                    ]}
                >
                    <Input placeholder="First Name"/>
                </Form.Item>
                <Form.Item
                    name={`lastName`}
                    rules={[
                        {
                            required: false,
                        },
                    ]}
                >
                    <Input placeholder="Last Name"/>
                </Form.Item>
                <Form.Item
                    name={`email`}
                    rules={[
                        {
                            required: false,
                        },
                    ]}
                >
                    <Input placeholder="E-mail"/>
                </Form.Item>
            </Row>
        );
        return children;
    };

    const onSearch = async (values) => {
        console.log('Received values of form: ', values);
        try {
            const response = await axios.get('/users/all', {
                params: {
                    firstName: values.firstName,
                    lastName: values.lastName,
                    email: values.email,
                }
            });
            console.log(response.data);
            setUsers(response.data)
        } catch (error) {
            console.error('Błąd:', error);
        }
    }
    const handleEditClick = (id) => {
        navigate(`/user/${id}`)
    }

    const fetchUsers = async () => {
        try {
            const response = await getRequest('/users/all');
            setUsers(response.data);
        } catch (error) {
            console.error('Błąd podczas pobierania użytkowników:', error);
        }
    };

    const confirm = async (id) => {
        try {
            await deleteRequest(`/users/${id}`);
            message.success('User deleted');
            await fetchUsers();
        } catch (error) {
            console.error('Błąd podczas usuwania użytkownika:', error);
            message.error('Nie udało się usunąć użytkownika.');
        }
    };
    const cancel = (e) => {
        console.log(e);
    };

    return <>
        <h1 style={{color: '#F48668'}}>Users</h1>
        <Form form={form} name="advanced_search" onFinish={onSearch}>
            <Row gutter={24}>{getFields()}
                <div
                    style={{
                        textAlign: 'right',
                    }}
                >
                    <Space size="small">
                        <Button type="primary" htmlType="submit">
                            Search
                        </Button>
                        <Button
                            onClick={() => {
                                form.resetFields();
                            }}
                        >
                            Clear
                        </Button>
                    </Space>
                </div>
            </Row>
        </Form>
        <Row wrap={true} style={{maxWidth: 1200}} gutter={[16, 16]}>
            {users.map(user => (
                <Col key={user.id}>
                    <Card
                        style={{
                            width: 300,
                        }}
                        cover={
                            <img
                                alt="example"
                                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
                            />
                        }
                        actions={[
                            <EditOutlined key="edit" onClick={() => handleEditClick(user.id)}/>,
                            <Popconfirm
                                title="Delete the user"
                                description="Are you sure to delete this user?"
                                onConfirm={() => confirm(user.id)}
                                onCancel={cancel}
                                okText="Yes"
                                cancelText="No"
                            >
                                <CloseOutlined key="delete" style={{color: '#FF6962'}}/>
                            </Popconfirm>
                        ]}
                    >
                        <Meta
                            avatar={getAvatar(user)}
                            title={`${user.firstName} ${user.lastName}`}
                            description={`${user.email}`}
                        />
                    </Card>
                </Col>
            ))}
        </Row>

    </>
}

export default UsersPage