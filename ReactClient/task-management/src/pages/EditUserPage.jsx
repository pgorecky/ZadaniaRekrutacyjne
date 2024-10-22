import {getRequest} from "../service/API_CONFIG";
import {Alert, Button, Form, Input} from "antd";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";

const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 6,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 14,
        },
    },
};

const EditUserPage = () => {
    const [componentVariant, setComponentVariant] = useState('filled');
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const {id} = useParams();
    const [form] = Form.useForm();

    const onFormVariantChange = ({variant}) => {
        setComponentVariant(variant);
    };

    useEffect(() => {
        getRequest(`/users/${id}`)
            .then(r => {
                form.setFieldsValue({
                    firstName: r.data.firstName,
                    lastName: r.data.lastName,
                    email: r.data.email
                });
            })
            .catch(error => {
                console.error("Error fetching user data:", error);
            });
    }, [id, form]);

    function submitForm(values) {
        console.log(values);
        axios.put(`/users/${id}`, values)
            .then(() => {
                setSuccessMessage('Successfully edited user');
            })
            .catch(error => {
                if (error.response && error.response.data) {
                    const firstname = error.response.data.firstName || "";
                    const lastname = error.response.data.lastName || "";
                    const email = error.response.data.email || "";

                    setErrorMessage(`${firstname}, ${lastname}, ${email}`);

                    if (error.response.status === 409) {
                        setErrorMessage("Email already exists");
                    }
                }
            });
    }

    return <>
        <h1 style={{color: '#F48668'}}>Edit user</h1>
        <Form
            {...formItemLayout}
            form={form}
            onValuesChange={onFormVariantChange}
            variant={componentVariant}
            style={{
                minWidth: 600,
                maxWidth: 800,
            }}
            initialValues={{
                variant: componentVariant,
            }}
            onFinish={submitForm}
        >

            <Form.Item
                label="First Name"
                name="firstName"
                rules={[
                    {
                        required: true,
                        message: 'Please input first name!',
                    },
                ]}
            >
                <Input placeholder="First Name"/>
            </Form.Item>

            <Form.Item
                label="Last Name"
                name="lastName"
                rules={[
                    {
                        required: true,
                        message: 'Please input last name!',
                    },
                ]}
            >
                <Input placeholder="Last Name"/>
            </Form.Item>

            <Form.Item
                name="email"
                label="E-mail"
                rules={[
                    {
                        type: 'email',
                        message: 'The input is not valid E-mail!',
                    },
                    {
                        required: true,
                        message: 'Please input your E-mail!',
                    },
                ]}
            >
                <Input placeholder="Input user e-mail"/>
            </Form.Item>

            <Form.Item
                wrapperCol={{
                    offset: 6,
                    span: 16,
                }}
            >
                <Button type="primary" htmlType="submit">
                    Edit
                </Button>
            </Form.Item>
            {successMessage && (
                <Alert
                    message="Success"
                    description={successMessage}
                    type="success"
                    showIcon
                    closable
                    onClose={() => setSuccessMessage('')}
                />
            )}
            {errorMessage && (
                <Alert
                    message="Failure to add user"
                    description={errorMessage}
                    type="error"
                    showIcon
                    closable
                    onClose={() => setErrorMessage('')}
                />
            )}
        </Form>
    </>
}

export default EditUserPage;
