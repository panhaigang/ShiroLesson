package cn.et.lesson02.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.et.lesson02.dao.Emp2Mapper;
import cn.et.lesson02.entity.Emp2;
import cn.et.lesson02.service.Emp2Service;





@Service
public class Emp2ServliceImpl implements Emp2Service {
	@Autowired
	Emp2Mapper emp2;
	
	public void saveEmp(Emp2 s) {
		emp2.insertSelective(s);
	}

	public void deleteEmp(Integer empno) {
		
		emp2.deleteByPrimaryKey(empno);
	}
	
	public void updateEmp(Emp2 s){
		emp2.updateByPrimaryKey(s);
	}
}
