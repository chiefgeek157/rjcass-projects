package com.rjcass.member.ifbsi;

import com.rjcass.member.Member;
import com.rjcass.member.MemberService;
import com.rjcass.service.spi.AbstractBusinessService;

public class IfbsiMemberService extends AbstractBusinessService implements MemberService
{
	private MemberFactory mMemberFactory;

	public IfbsiMemberService(MemberFactory factory)
	{
		mMemberFactory = factory;
	}

	@Override
	public Member getMemberById(String id)
	{
		Member member = mMemberFactory.createMember();
		member.setId(id);
		member.setName("Joe " + id);
		return member;
	}
}
