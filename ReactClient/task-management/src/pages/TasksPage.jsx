import React, {useEffect, useState} from "react";
import {deleteRequest, getRequest} from "../service/API_CONFIG";
import {
    Avatar,
    Button,
    Collapse,
    DatePicker,
    Dropdown,
    Form,
    Input, Menu, message, Popconfirm,
    Progress,
    Row,
    Select,
    Space,
    Tooltip
} from "antd";
import axios from "axios";
import getRandomPastelColor, {getInitials} from "../utils/Utils";
import {DeleteOutlined, EditOutlined, MoreOutlined} from "@ant-design/icons";
import {useNavigate} from "react-router-dom";

const {Option} = Select;
const {RangePicker} = DatePicker;

const TasksPage = () => {
    const [tasks, setTasks] = useState([])
    const [form] = Form.useForm();
    const navigate = useNavigate()

    useEffect(() => {
        getRequest('/tasks/all').then(r => {
            setTasks(r.data)
        });
    }, []);

    const onChange = (key) => {
        console.log(key);
    };

    const getProgressBar = (status) => {
        switch (status) {
            case 'SUBMITTED':
                return <Progress percent={10} size="small"/>
            case 'IN_PROGRESS':
                return <Progress percent={60} size="small" status="active"/>
            case 'REOPENED':
                return <Progress percent={70} size="small" status="exception"/>
            case 'DONE':
                return <Progress percent={100} size="small"/>
        }
    }

    const getAssignedUsers = (assignedUsers) => {
        return assignedUsers.map(user => {
            const initials = getInitials(user.firstName, user.lastName);
            const randomColor = getRandomPastelColor();

            return (
                <Tooltip title={`${user.firstName} ${user.lastName}`} placement="top" key={user.id}>
                    <Avatar style={{backgroundColor: randomColor}}>
                        {initials}
                    </Avatar>
                </Tooltip>
            );
        });
    };

    const handleEditClick = (id) => {
        navigate(`/task/${id}`)
    }

    const fetchTasks = async () => {
        try {
            const response = await getRequest('/tasks/all');
            setTasks(response.data);
        } catch (error) {
            console.error('Błąd podczas pobierania tasków:', error);
        }
    };

    const confirm = async (id) => {
        try {
            await deleteRequest(`/tasks/${id}`);
            message.success('Task deleted');
            await fetchTasks();
        } catch (error) {
            console.error('Błąd podczas usuwania taska:', error);
            message.error('Nie udało się usunąć zadania.');
        }
    };
    const cancel = (e) => {
        console.log(e);
    };

    const dropdownMenu = (id) => (
        <Menu>
            <Menu.Item key="1" icon={<EditOutlined/>} onClick={() => handleEditClick(id)}>
                Edit
            </Menu.Item>
            <Menu.Item key="2" icon={<DeleteOutlined/>} danger>
                <Popconfirm
                    title="Delete the task"
                    description="Are you sure to delete this task?"
                    onConfirm={() => confirm(id)}
                    onCancel={cancel}
                    okText="Yes"
                    cancelText="No"
                >
                    Delete
                </Popconfirm>
            </Menu.Item>
        </Menu>
    );

    const genExtra = (taskId) => (
        <Dropdown overlay={() => dropdownMenu(taskId)} trigger={['click']}>
            <MoreOutlined/>
        </Dropdown>
    );

    const items = tasks.map((task) => ({
        key: task.id,
        label: task.title,
        children: (
            <>
                <p>{task.description}</p>
                <div
                    style={{
                        display: "flex",
                        flexDirection: "row",
                        alignItems: "center",
                        justifyContent: "space-around",
                    }}
                >
                    <p>Deadline: {`${task.deadline[0]}-${String(task.deadline[1]).padStart(2, "0")}-${String(task.deadline[2]).padStart(2, "0")}`}</p>
                    <div>
                        <p>Status: {task.status} </p>
                        {getProgressBar(task.status)}
                    </div>
                    <div>
                        <p>Assigned Users:</p>
                        <Avatar.Group>
                            {getAssignedUsers(task.assignedUsers)}
                        </Avatar.Group>
                    </div>
                </div>
            </>
        ),
        extra: genExtra(task.id),
    }));

    const getFields = () => {
        const children = [];
        children.push(
            <Row>
                <Form.Item
                    name={`title`}
                    rules={[
                        {
                            required: false,
                        },
                    ]}
                >
                    <Input placeholder="Title"/>
                </Form.Item>
                <Form.Item
                    name={`description`}
                    rules={[
                        {
                            required: false,
                        },
                    ]}
                >
                    <Input placeholder="Description"/>
                </Form.Item>
                <Form.Item
                    name="status"
                    rules={[
                        {
                            required: false,
                        },
                    ]}
                >
                    <Select placeholder="Status">
                        <Option value="SUBMITTED">Submitted</Option>
                        <Option value="IN_PROGRESS">In Progress</Option>
                        <Option value="REOPENED">Reopened</Option>
                        <Option value="DONE">Done</Option>
                    </Select>
                </Form.Item>

                <Form.Item
                    name="deadline"
                    rules={[
                        {
                            required: false,
                        },
                    ]}
                >
                    <RangePicker/>
                </Form.Item>
            </Row>
        );
        return children;
    };

    const onSearch = async (values) => {
        console.log('Received values of form: ', values);
        let deadlineFrom;
        let deadlineTo;
        const deadlines = values.deadline;
        if (deadlines && deadlines.length > 0) {
            const startDeadline = deadlines[0];
            const endDeadline = deadlines[deadlines.length - 1];

            const yearFrom = startDeadline.$y;
            const monthFrom = startDeadline.$M + 1;
            const dayFrom = startDeadline.$D;

            deadlineFrom = `${yearFrom}-${String(monthFrom).padStart(2, '0')}-${String(dayFrom).padStart(2, '0')}`;

            const yearTo = endDeadline.$y;
            const monthTo = endDeadline.$M + 1;
            const dayTo = endDeadline.$D;

            deadlineTo = `${yearTo}-${String(monthTo).padStart(2, '0')}-${String(dayTo).padStart(2, '0')}`;
        }


        try {
            const response = await axios.get('/tasks/all', {
                params: {
                    title: values.title,
                    description: values.description,
                    status: values.status,
                    deadlineFrom: deadlineFrom,
                    deadlineTo: deadlineTo
                }
            });
            console.log(response.data);
            // setItems([])
            setTasks(response.data)
        } catch (error) {
            console.error('Błąd:', error);
        }
    }

    return <>
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
        <Collapse style={{minWidth: 1000}}
                  onChange={onChange}
                  items={items}
        />
    </>
}

export default TasksPage