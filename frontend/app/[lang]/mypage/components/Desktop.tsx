import { container } from '@/app/common'
import { Profile } from '@/app/types/profile'
import React from 'react'

interface Props {
    profile: Profile | undefined
}

export function Desktop({ profile }: Props) {
    return (
        <div className={container.main.desktop}>
            <div className="h-[250px] flex flex-col justify-center items-center gap-4 my-8 select-none">
                <img className="min-w-[90px] min-h-[90px] object-contain border rounded-full" src={profile?.profileImage || ''} alt="" />
                <div className="h-[57px]">
                    <div className="text-[24px] text-center">{profile?.nickname}</div>
                    <div className="text-[14px] text-gray-600 text-center">{profile?.email}</div>
                </div>
            </div>
        </div>
    )
}
