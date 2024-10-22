import {Avatar, Badge, Descriptions, Progress, Tooltip} from "antd";
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {getRequest} from "../service/API_CONFIG";
import getRandomPastelColor, {getInitials} from "../utils/Utils";
import {EditOutlined} from "@ant-design/icons";

const TaskPage = () => {
    const {id} = useParams();
    const [title, setTitle] = useState('')
    const [description, setDescription] = useState('')
    const [status, setStatus] = useState('')
    const [deadline, setDeadline] = useState([])
    const [assignedUsers, setAssignedUsers] = useState([])
    const navigate = useNavigate()

    useEffect(() => {
        getRequest(`/tasks/${id}`)
            .then(r => {
                setTitle(r.data.title)
                setDescription(r.data.description)
                setStatus(r.data.status)
                setDeadline(r.data.deadline)
                setAssignedUsers(r.data.assignedUsers)
            })
    }, [id]);

    const getStatusBadge = (status) => {
        switch (status) {
            case 'SUBMITTED':
                return <Badge status="default" text="Submitted"/>
            case 'IN_PROGRESS':
                return <Badge status="processing" text="In progress"/>
            case 'REOPENED':
                return <Badge status="error" text="Reopened"/>
            case 'DONE':
                return <Badge status="success" text="Done"/>
        }
    }

    const getAssignedUsers = () => {
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

    return <div style={{
        display: "flex",
        flexDirection: "row",
        alignItems: "start",
        justifyContent: "start"
    }}>
        <Descriptions title="Task Info" bordered layout={'horizontal'} column={1} style={{minWidth: 400, maxWidth: 800}}>
            <Descriptions.Item label="Title">{title}</Descriptions.Item>
            <Descriptions.Item label="Description">{description}</Descriptions.Item>
            <Descriptions.Item label="Status">{getStatusBadge(status)}</Descriptions.Item>
            <Descriptions.Item label="Deadline">{`${deadline[0]}-${String(deadline[1]).padStart(2, "0")}-${String(deadline[2]).padStart(2, "0")}`}</Descriptions.Item>
            <Descriptions.Item label="Assigned Users">
                <Avatar.Group>
                    <Avatar src="https://api.dicebear.com/7.x/miniavs/svg?seed=1"/>
                    {getAssignedUsers()}
                </Avatar.Group>
            </Descriptions.Item>
        </Descriptions>
        <EditOutlined style={{fontSize: '1.5rem', color: "gray", cursor: "pointer"}} onClick={() => navigate(`/task/${id}/edit`)}/>
    </div>
}

export default TaskPage