import React, {useEffect, useState} from 'react';
import {
    Alert,
    Button,
    DatePicker,
    Form,
    Input,
    Select,
} from 'antd';
import {getRequest, postRequest} from "../service/API_CONFIG";

const {Option} = Select;
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
const CreateTaskPage = () => {
    const [componentVariant, setComponentVariant] = useState('filled');
    const [users, setUsers] = useState([])
    const [successMessage, setSuccessMessage] = useState('')
    const [errorMessage, setErrorMessage] = useState('')
    const onFormVariantChange = ({variant}) => {
        setComponentVariant(variant);
    };

    const options = [];

    useEffect(() => {
        getRequest('/users/all').then(r => {
            setUsers(r.data)
        });

    }, []);

    users.map(user => {
        options.push({
            label: `${user.firstName} ${user.lastName}`,
            value: user.id
        })
    });

    function submitForm(values) {
        const formattedValues = {
            ...values,
            deadline: values.deadline ? values.deadline.format('YYYY-MM-DD') : null
        };

        console.log(formattedValues);
        postRequest('/tasks', formattedValues)
            .then(() => {
                setSuccessMessage('Successfully created task');
            })
            .catch(error => {
                if (error.response && error.response.data) {
                    const title = error.response.data.title || "";
                    const description = error.response.data.description || "";

                    setErrorMessage(`${title}, ${description}`);
                }
            });
    }

    return (
        <>
            <h1 style={{color: '#F48668'}}>Create a new task</h1>
            <Form
                {...formItemLayout}
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
                    label="Title"
                    name="title"
                    rules={[
                        {
                            required: true,
                            message: 'Please input task title!',
                        },
                    ]}
                >
                    <Input placeholder="Please input title"/>
                </Form.Item>

                <Form.Item
                    label="Description"
                    name="description"
                    rules={[
                        {
                            required: true,
                            message: 'Describe the content of the task!',
                        },
                    ]}
                >
                    <Input.TextArea placeholder="Please input description"/>
                </Form.Item>

                <Form.Item
                    label="Status"
                    name="status"
                    rules={[
                        {
                            required: true,
                            message: 'Please input status!',
                        },
                    ]}
                >
                    <Select placeholder="Set actual status">
                        <Option value="SUBMITTED">Submitted</Option>
                        <Option value="IN_PROGRESS">In Progress</Option>
                        <Option value="REOPENED">Reopened</Option>
                        <Option value="DONE">Done</Option>
                    </Select>
                </Form.Item>

                <Form.Item
                    label="Assigned users"
                    name="assignedUsers"
                    rules={[
                        {
                            required: false,
                        },
                    ]}
                >
                    <Select
                        placeholder="Assign users"
                        mode="multiple"
                        options={options}>
                    </Select>
                </Form.Item>

                <Form.Item
                    label="Deadline"
                    name="deadline"
                    rules={[
                        {
                            required: true,
                            message: 'Please deadline!',
                        },
                    ]}
                >
                    <DatePicker/>
                </Form.Item>

                <Form.Item
                    wrapperCol={{
                        offset: 6,
                        span: 16,
                    }}
                >
                    <Button type="primary" htmlType="submit" onClick={submitForm}>
                        Create
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
                        message="Failure to create task"
                        description={errorMessage}
                        type="error"
                        showIcon
                        closable
                        onClose={() => setErrorMessage('')}
                    />
                )}
            </Form>
        </>
    );
};

export default CreateTaskPage