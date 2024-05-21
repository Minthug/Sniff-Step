import { container } from '@/app/common'
import { Profile } from '@/app/types/profile'
import React from 'react'

interface Props {
    profile: Profile | undefined
}

export function Mobile({ profile }: Props) {
    return (
        <div className={container.main.mobile}>
            <div className="h-[500px] flex flex-col justify-center items-center gap-4 my-8 select-none">
                <img className="min-w-[180px] min-h-[180px] object-contain border rounded-full" src={profile?.profileImage || ''} alt="" />
                <div className="h-[57px]">
                    <div className="text-[24px] text-center">{profile?.nickname}</div>
                    <div className="text-[14px] text-gray-600 text-center">{profile?.email}</div>
                </div>
            </div>
        </div>
    )
}
