package cn.et.lesson02.service;

import cn.et.lesson02.entity.Emp;
import cn.et.lesson02.utils.PageTools;

public interface EmpService {

	//��ѯ
	public abstract PageTools queryEmp1(String ename,Integer page,Integer rows);

	//����
	public abstract void saveEmp(Emp emp);
	
	//ɾ��
	public abstract void deleteEmp(Integer empno);
	
	//�޸�
	public abstract void updateEmp(Emp emp);
}