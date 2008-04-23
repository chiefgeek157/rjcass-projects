package com.rjcass.member;

import com.rjcass.service.BusinessService;

public interface MemberService extends BusinessService
{
	Member getMemberById(String id);
}
