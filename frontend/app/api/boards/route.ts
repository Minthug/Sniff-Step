import { NextRequest, NextResponse } from 'next/server'

export function GET(req: NextRequest) {
    const boards = [
        {
            id: 1,
            title: '산본동 산책시켜드립니다',
            address: '경기도 군포시 산본동',
            ownerSatisfaction: 10,
            imageUrl: '/images/logo1-removebg-preview.png',
            createdAt: new Date()
        },
        {
            id: 2,
            title: '서울대입구 산책시켜드립니다',
            address: '서울특별시 관악구 봉천동',
            ownerSatisfaction: 10,
            imageUrl: '/images/text-logo.png',
            createdAt: new Date()
        },
        {
            id: 3,
            title: '신림동 산책시켜드립니다',
            address: '서울특별시 관악구 신림동',
            ownerSatisfaction: 10,
            imageUrl: '/images/loginSide.webp',
            createdAt: new Date()
        },
        {
            id: 4,
            title: '서울대입구 산책시켜드립니다',
            address: '서울특별시 관악구 봉천동',
            ownerSatisfaction: 10,
            imageUrl: '/images/logo1-removebg-preview.png',
            createdAt: new Date()
        },
        {
            id: 5,
            title: '신림동 산책시켜드립니다',
            address: '서울특별시 관악구 신림동',
            ownerSatisfaction: 10,
            imageUrl: '/images/logo1-removebg-preview.png',
            createdAt: new Date()
        }
    ]
    return NextResponse.json({ data: boards }, { status: 200 })
}
