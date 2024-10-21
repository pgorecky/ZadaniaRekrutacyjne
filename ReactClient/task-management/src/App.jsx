import React, {useEffect, useState} from 'react';
import {
    FileOutlined, FolderOutlined,
    PieChartOutlined,
    TeamOutlined, UserAddOutlined,
    UserOutlined,
} from '@ant-design/icons';
import {Layout, Menu} from 'antd';
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import {getRequest} from "./service/API_CONFIG";

const {Footer, Sider} = Layout;


const App = () => {
    const [collapsed, setCollapsed] = useState(false);
    const [tasks, setTasks] = useState([])
    const [users, setUsers] = useState([])
    const navigate = useNavigate()
    const location = useLocation()

    useEffect(() => {
        getRequest('/tasks/all').then(r => {
            setTasks(r.data)
        });
        getRequest('/users/all').then(r => {
            setUsers(r.data)
        });

    }, []);

    function getItem(label, key, icon, children) {
        return {
            key,
            icon,
            children,
            label,
        };
    }

    function handleClick(key) {
        navigate(key)
    }

    const getUserItems = users.map(user => getItem(`${user.firstName}  ${user.lastName}`, '/user/' + user.id));
    const getTaskItems = tasks.map(task => getItem(task.title, '/task/' + task.id));

    const items = [
        getItem('+ Create new task', '/task/create', <PieChartOutlined/>),
        getItem('+ Add new user', '/user/add', <UserAddOutlined />),
        getItem('Search task', '/task/all', <FolderOutlined />),
        getItem('Search user', '/user/all', <UserOutlined />),
        getItem('Tasks', 'sub1', <FileOutlined/>, getTaskItems),
        getItem('Users', 'sub2', <TeamOutlined/>, getUserItems),
    ];

    return (
        <Layout
            style={{
                minHeight: '100vh',
            }}
        >
            <Sider collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
                <div className="demo-logo-vertical"/>
                <Menu theme="light" defaultSelectedKeys={[location.pathname]} mode="inline" items={items} onSelect={(item) => handleClick(item.key)}/>
            </Sider>
            <Layout>
                <div
                style={{
                    display: "flex",
                    flexDirection: 'column',
                    justifyContent: "center",
                    alignItems: "center",
                    height: "100%",
                }}>
                    <Outlet/>
                </div>
                <Footer
                    style={{
                        textAlign: 'center',
                        position: 'fixed',
                        width: '100%',
                        bottom: 0
                    }}
                >
                    Task Management ©{new Date().getFullYear()} Created by Patryk Górecki
                </Footer>
            </Layout>
        </Layout>
    );
};
export default App;